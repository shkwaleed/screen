package com.example.xibo

import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import org.json.JSONObject
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.InputSource
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

class ExtractionUtil {
    fun parseRequiredFilesResponse(jsonString: String): List<Map<String, String>> {
        val extractedDataList = mutableListOf<Map<String, String>>()

        try {
            val jsonObject = JSONObject(jsonString)
            val bodyObject = jsonObject.getJSONObject("Body")
            val requiredFilesResponseObject = bodyObject.getJSONObject("RequiredFilesResponse")
            val requiredFilesXmlObject = requiredFilesResponseObject.getJSONObject("RequiredFilesXml")
//            val newBody = xmlToJson(requiredFilesXmlObject.toString())
            val filesXmlString = requiredFilesXmlObject.getString("")
            val filesXmlObject = JSONObject(filesXmlString)
            val xmlString = filesXmlObject.getString("")
            val xmlDocument = parseXml(xmlString)
            val attributes = xmlDocument.documentElement.attributes
            Log.e("Attributes",attributes.item(3).nodeName.toString())

            val files = requiredFilesXmlObject["files"] as Map<*, *>
            val fileList = files["file"] as List<*>

            for (i in 0 until fileList.size) {
                val fileData = fileList[i] as Map<*, *>
                val dataMap = mutableMapOf<String, String>()

                for ((key, value) in fileData) {
                    dataMap[key as String] = value as String
                }

                extractedDataList.add(dataMap)
            }
        } catch (e: Exception) {
            // Handle exceptions here
            e.printStackTrace()
        }

        return extractedDataList
    }

}
data class FileData(
    val download: String?,
    val size: String?,
    val md5: String?,
    val saveAs: String?,
    val type: String?,
    val path: String?,
    val id: String?,
    val fileType: String?,
    val layoutid: String?,
    val regionid: String?,
    val mediaid: String?,
    val updated: String?
)

fun parseXml(xmlString: String): Document {
    val factory = DocumentBuilderFactory.newInstance()
    val builder = factory.newDocumentBuilder()
    val inputSource = InputSource(StringReader(xmlString))
    return builder.parse(inputSource)
}
