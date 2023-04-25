package com.troForum_server.application.ocr

import com.tencentcloudapi.common.Credential
import com.tencentcloudapi.common.exception.TencentCloudSDKException
import com.tencentcloudapi.common.profile.ClientProfile
import com.tencentcloudapi.common.profile.HttpProfile
import com.tencentcloudapi.ocr.v20181119.OcrClient
import com.tencentcloudapi.ocr.v20181119.models.GeneralBasicOCRRequest
import com.tencentcloudapi.ocr.v20181119.models.GeneralBasicOCRResponse
import org.springframework.stereotype.Service


@Service
class OcrService {
    fun ocr(img: String): String{
        val tsecretId = "AKID0tcCbomXDm9LMAmGSRFuOgjjX7lJl03j"
        val tsecretKey = "oRr33b3INcqsfP7lNtUu9B0c2Qjx5MSJ"

        try {
            //配置参数
            val cred = Credential(tsecretId, tsecretKey)
            val httpProfile = HttpProfile()
            httpProfile.endpoint = "ocr.tencentcloudapi.com"
            val clientProfile = ClientProfile()
            clientProfile.httpProfile = httpProfile
            val client = OcrClient(cred, "ap-beijing", clientProfile)

            //放入图片Base64格式
            val req = GeneralBasicOCRRequest()
            req.imageBase64 = img
            val resp = client.GeneralBasicOCR(req)
            return GeneralBasicOCRResponse.toJsonString(resp)
        } catch (e: TencentCloudSDKException) {
            return e.toString()
        }
    }
}
