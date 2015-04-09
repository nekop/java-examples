package com.redhat.gss.mockxa;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.util.Arrays;

import javax.transaction.xa.Xid;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.XAException;
import org.jboss.logging.Logger;

public class MockXAResource implements XAResource {

    private static Logger log = Logger.getLogger(MockXAResource.class);

    public int resourceId = 0;
    public boolean crashInPrepare = false;
    public boolean crashInRollback = false;
    public boolean crashInCommit = false;
    public boolean exceptionInPrepare = false;
    public boolean exceptionInEnd = false;
    public boolean exceptionInRollback = false;
    public boolean exceptionInCommit = false;
    public boolean exceptionInRecover = false;
    public boolean hangInPrepare = false;
    public boolean hangInCommit = false;
    public boolean logException = true;
    public  int exceptionErrorCode = -1;
    private int transactionTimeout = 30000;

    public void start(Xid xid, int flags) throws XAException {
        log.info("start('" + xid + "', " + flags + ')');
    }
    
    public void end(Xid xid, int flags) throws XAException {
        log.info("end('" + xid + "', " + flags + ')');
        if (exceptionInEnd) {
            XAException ex = new XAException(exceptionErrorCode);
            if (logException) {
                log.info("logException", ex);
            }
            throw ex;
        }
    }

    public int prepare(Xid xid) throws XAException {
        log.info("prepare('" + xid + "')");
        if (crashInPrepare) {
            Runtime.getRuntime().halt(0);
        }
        if (exceptionInPrepare) {
            XAException ex = new XAException(exceptionErrorCode);
            if (logException) {
                log.info("logException", ex);
            }
            throw ex;
        }
        if (hangInPrepare) {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException ignore) { }
        }
        // Save the xid before return XA_OK
        saveRecoverXid(xid);
        return XAResource.XA_OK;
    }

    public void commit(Xid xid, boolean onePhase) throws XAException {
        log.info("commit('" + xid + "', " + onePhase + ')');
        if (crashInCommit) {
            Runtime.getRuntime().halt(0);
        }
        if (exceptionInCommit) {
            XAException ex = new XAException(exceptionErrorCode);
            if (logException) {
                log.info("logException", ex);
            }
            throw ex;
        }
        if (hangInCommit) {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException ignore) { }
        }
        // Commit suceeds, delete xid from 
        deleteRecoverXid(xid);
    }

    public void rollback(Xid xid) throws XAException {
        log.info("rollback('" + xid + "')");
        if (crashInRollback) {
            Runtime.getRuntime().halt(0);
        }
        if (exceptionInRollback) {
            XAException ex = new XAException(exceptionErrorCode);
            if (logException) {
                log.info("logException", ex);
            }
            throw ex;
        }
        deleteRecoverXid(xid);
    }

    public void forget(Xid xid) throws XAException {
        log.trace("forget('" + xid + "')");
    }

    public Xid[] recover(int flags) throws XAException {
        log.info("recover('" + flags + "')");
        if (exceptionInRecover) {
            XAException ex = new XAException(exceptionErrorCode);
            if (logException) {
                log.info("logException", ex);
            }
            throw ex;
        }
        Xid[] result = loadRecoverXids();
        log.info("recover() returns: " + Arrays.asList(result));
        return result;
    }

    public boolean isSameRM(XAResource xaResource) throws XAException {
        boolean result =
            this.resourceId == ((MockXAResource)xaResource).resourceId;
        log.info("isSameRM() returns: " + result);
        return result;
    }

    public int getTransactionTimeout() throws XAException {
        return transactionTimeout;
    }

    public boolean setTransactionTimeout(int transactionTimeout) throws XAException {
        this.transactionTimeout = transactionTimeout;
        return true;
    }


    private Xid[] loadRecoverXids() {
        File dataDir = new File(System.getProperty("jboss.server.data.dir"));
        File debugDir = new File(dataDir, "debug");
        if (!debugDir.exists()) {
            return new Xid[0];
        }
        File[] files = debugDir.listFiles();
        Xid[] xids = new Xid[files.length];
        try {
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                ObjectInputStream ois =
                    new ObjectInputStream(
                        new BufferedInputStream(
                            new FileInputStream(f)));
                Object o = ois.readObject();
                ois.close();
                xids[i] = (Xid)o;
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error when load recoverXid", ex);
        }
        return xids;
    }

    private void saveRecoverXid(Xid xid) {
        log.info("saveRecoverXid(): " + xid);
        File dataDir = new File(System.getProperty("jboss.server.data.dir"));
        File debugDir = new File(dataDir, "debug");
        if (!debugDir.exists()) {
            debugDir.mkdir();
        }
        File file = new File(debugDir, xid.toString());
        try {
            ObjectOutputStream oos =
                new ObjectOutputStream(
                    new BufferedOutputStream(
                        new FileOutputStream(file)));
            oos.writeObject(xid);
            oos.flush();
            oos.close();
        } catch (Exception ex) {
            throw new RuntimeException("Error when save recoverXid", ex);
        }
    }

    private void deleteRecoverXid(Xid xid) {
        log.info("deleteRecoverXid(): " + xid);
        File dataDir = new File(System.getProperty("jboss.server.data.dir"));
        File debugDir = new File(dataDir, "debug");
        File file = new File(debugDir, xid.toString());
        if (file.exists()) {
            boolean success = file.delete();
            if (!success) {
                log.warn("Failed to delete recoverXid: " + file);
            }
        }
    }
}
