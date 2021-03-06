require 'java'

JBOSS_HOME="/home/nekop/eap6"

require "./target/ee6-ejb-interfaces.jar"
require "#{JBOSS_HOME}/bin/client/jboss-client.jar"
require "#{JBOSS_HOME}/modules/system/layers/base/org/jboss/resteasy/resteasy-jaxrs/main/resteasy-jaxrs-2.3.7.Final-redhat-2.jar"
require "#{JBOSS_HOME}/modules/system/layers/base/javax/ws/rs/api/main/jboss-jaxrs-api_1.1_spec-1.0.1.Final-redhat-2.jar"

java_import "java.util.Properties"
java_import "javax.naming.Context"
java_import "javax.naming.InitialContext"

def initial_context
  p = Properties.new()
  p.put("remote.connections", "default")
  p.put("remote.connection.default.port", "4447")
  p.put("remote.connection.default.host", "localhost")
  p.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false")
  p.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming")
  p.put("org.jboss.ejb.client.scoped.context", true)
  InitialContext.new(p)
end

def corba_initial_context
  p = Properties.new()
  p.put(Context.INITIAL_CONTEXT_FACTORY,
        "com.sun.jndi.cosnaming.CNCtxFactory")
  p.put(Context.PROVIDER_URL, "corbaloc:iiop:localhost:3528/JBoss/Naming/root")
  InitialContext.new(p)
end

def hello_slsb(ejb_context)
  ear_name = "ee6-ejb-interfaces"
  ejbjar_name = "ee6-ejb-interfaces"
  ejb_name = "Hello"
  interface_name = "com.github.nekop.examples.HelloRemote"
  ejb_context.lookup("#{ear_name}/#{ejbjar_name}/#{ejb_name}!#{interface_name}")
end

type = ARGV.shift
case type
when "native"
  ejb_context = initial_context.lookup("ejb:")
  begin
    bean = hello_slsb(ejb_context)
    bean.hello("world")
  ensure
    begin
      ejb_context.close
    rescue
      # no-op
    end
  end
when "iiop"
  java_import "com.github.nekop.examples.HelloEJB2Home"
  java.lang.System::setProperty("com.sun.CORBA.ORBUseDynamicStub", "true")
  o = corba_initial_context.lookup("Hello")
  home = javax.rmi.PortableRemoteObject.narrow(o, HelloEJB2Home.java_class)
  bean = home.create()
  bean.hello("world")
when "ws"
  java_import "java.net.URL"
  java_import "javax.xml.namespace.QName"
  java_import "javax.xml.ws.Service"
  java_import "com.github.nekop.examples.HelloLocal"
  ejbjar_name = "ee6-ejb-interfaces"
  ejb_name = "Hello"
  wsdlLocation = URL.new("http://127.0.0.1:8080/#{ejbjar_name}/#{ejb_name}?wsdl")
  serviceName = QName.new("http://examples.nekop.github.com/", "#{ejb_name}Service")
  portName = QName.new("http://examples.nekop.github.com/", "#{ejb_name}Port")
  service = Service.create(wsdlLocation, serviceName)
  bean = service.getPort(portName, HelloLocal.java_class)
  bean.hello("world")
when "rs"
  java_import "org.jboss.resteasy.client.ClientRequest"
  java_import "org.jboss.resteasy.client.core.executors.URLConnectionClientExecutor"
  war_name = "ee6-ejb-interfaces-web"
  url = "http://localhost:8080/#{war_name}/rest/hello/world"
  request = ClientRequest.new(url, URLConnectionClientExecutor.new)
  request.get(java.lang.String.java_class);  
else
  puts "unko"
end
