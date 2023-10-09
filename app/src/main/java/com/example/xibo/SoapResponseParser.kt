package com.example.xibo
import android.util.Log
import org.json.JSONObject
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.NamespaceList
import org.simpleframework.xml.Root
import org.simpleframework.xml.core.Persister
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.Element

import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

sealed class SoapResult {
    data class Success(val displayInfo: String) : SoapResult()
    data class Error(val message: String) : SoapResult()
}
fun convertXmlStringToJson(xmlString: String): JSONObject {
    val factory = DocumentBuilderFactory.newInstance()
    val builder = factory.newDocumentBuilder()
    val inputSource = InputSource(StringReader(xmlString))
    val document: Document = builder.parse(inputSource)

    // Now, convert the DOM document to JSON
    val rootNode = document.documentElement
    val json = convertElementToJson(rootNode)
    // Implement your logic here to traverse the DOM and populate the JSON object

    return json
}
fun convertElementToJson(element: Element): JSONObject {
    val json = JSONObject()

    val childNodes = element.childNodes
    for (i in 0 until childNodes.length) {
        val childNode = childNodes.item(i)
        if (childNode.nodeType == Node.ELEMENT_NODE) {
            val childElement = childNode as Element
            json.put(childElement.nodeName, convertElementToJson(childElement))
        } else if (childNode.nodeType == Node.TEXT_NODE) {
            json.put(element.nodeName, childNode.textContent)
        }
    }

    return json
}
class SoapResponseParser {

//    fun parseRegisterDisplayResponse(responseXml: String): SoapResult {
//        val serializer = Persister()
//        return try {
//            Log.e("respone trimmed",responseXml.toString())
//            val soapEnvelope = serializer.read(SOAPEnvelope::class.java, responseXml.trim())
//            val activationMessageXml = soapEnvelope.body.registerDisplayResponse.activationMessage
//            val displayInfo = serializer.read(DisplayInfo::class.java, activationMessageXml)
//            SoapResult.Success(displayInfo)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            SoapResult.Error("Failed to parse SOAP response.")
//        }
//    }

}

//@Root(name = "Envelope")
//@NamespaceList(
//    Namespace(prefix = "SOAP-ENV", reference = "http://schemas.xmlsoap.org/soap/envelope/"),
//    Namespace(prefix = "ns1", reference = "urn:xmds"),
//    Namespace(prefix = "xsd", reference = "http://www.w3.org/2001/XMLSchema"),
//    Namespace(prefix = "xsi", reference = "http://www.w3.org/2001/XMLSchema-instance"),
//    Namespace(prefix = "SOAP-ENC", reference = "http://schemas.xmlsoap.org/soap/encoding/")
//)
//data class SOAPEnvelope(
//    @field:Element(name = "Body")
//    var body: SOAPBody = SOAPBody()
//)
//
//@Root(name = "Body")
//data class SOAPBody(
//    @field:Element(name = "RegisterDisplayResponse")
//    var registerDisplayResponse: RegisterDisplayResponse = RegisterDisplayResponse()
//)
//
//@Root(name = "RegisterDisplayResponse")
//data class RegisterDisplayResponse(
//    @field:Element(name = "ActivationMessage")
//    var activationMessage: String = ""
//)