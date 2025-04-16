package com.electric.controller.net;

//import org.apache.axis2.transport.http.impl.httpclient4.HttpTransportPropertiesImpl;

/**
 * @author sunk
 * @date 2025/2/21
 */
public class NetTest {

    /**
     * 命名空间
     */
    private static final String TARGET_NAMESPACE = "http://api.spl.ruijie.com";

    /*public static void main(String[] args) {
        try {
            // 创建DocumentBuilderFactory和DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
    
            // 创建一个新的Document
            Document document = builder.newDocument();
    
            // 创建根元素soapenv:Envelope
            Element envelope = document.createElementNS("http://schemas.xmlsoap.org/soap/envelope/", "soapenv:Envelope");
            document.appendChild(envelope);
    
            // 设置命名空间
            envelope.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
            envelope.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:api", "http://api.spl.ruijie.com/");
    
            // 创建soapenv:Header元素
            Element header = document.createElementNS("http://schemas.xmlsoap.org/soap/envelope/", "soapenv:Header");
            envelope.appendChild(header);
    
            // 创建soapenv:Body元素
            Element body = document.createElementNS("http://schemas.xmlsoap.org/soap/envelope/", "soapenv:Body");
            envelope.appendChild(body);
    
            // 创建api:findAllUserTemplatePackages元素
            Element findAllUserTemplatePackages = document.createElementNS("http://api.spl.ruijie.com/", "api:findAllUserTemplatePackages");
            body.appendChild(findAllUserTemplatePackages);
    
            // 将Document转换为XML字符串
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
    
            String xmlString = writer.getBuffer().toString();
            System.out.println(xmlString);
    
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }*/

    /*public static void main(String[] args) {
        String address = "http://61.153.150.19:9072/sam/services/samapi?wsdl";
        String namespace = TARGET_NAMESPACE;
        String methodName = "findAllUserTemplatePackages";
        String userName = "kaifa";
        String password = "kaifazhe01";
    
        try {
            // 创建ServiceClient实例
            ServiceClient sender = new ServiceClient();
            EndpointReference endpointReference = new EndpointReference(address);
            Options options = new Options();
            options.setAction(namespace + methodName);
            options.setTo(endpointReference);
    
            // 设置认证信息
            HttpTransportPropertiesImpl.Authenticator basicAuth = new HttpTransportPropertiesImpl.Authenticator();
            basicAuth.setUsername(userName);
            basicAuth.setPassword(password);
            options.setProperty(HTTPConstants.AUTHENTICATE, basicAuth);
            options.setProperty(HTTPConstants.CHUNKED, false);
            // 设置HTTP客户端重用和缓存属性
            //options.setProperty(HTTPConstants.REUSE_HTTP_CLIENT, Boolean.TRUE);
            //options.setProperty(HTTPConstants.CACHED_HTTP_CLIENT, Boolean.TRUE);
            //options.setProperty(HTTPConstants.COOKIE_POLICY, Boolean.TRUE);
    
            sender.setOptions(options);
            OMFactory fac = OMAbstractFactory.getOMFactory();
    
            // 设置命名空间
            OMNamespace omNs = fac.createOMNamespace(namespace, "api");
            OMElement data = fac.createOMElement(methodName, omNs);
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println(data);
            // 发送数据，返回结果
            OMElement result = sender.sendReceive(data);
            String resultStr = escape(result.toString());
            System.out.println(resultStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 转义
     *
     * @param dangerous
     * @return
     */
    private static String escape(String dangerous) {
        String result = dangerous;
        result = result.replaceAll("&amp;", "&");
        result = result.replaceAll("&quot;", "/");
        result = result.replaceAll("&apos;", "'");
        result = result.replaceAll("&lt;", "<");
        result = result.replaceAll("&gt;", ">");
        return result;
    }
}