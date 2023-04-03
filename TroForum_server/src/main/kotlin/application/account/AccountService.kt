package com.troForum_server.application.account

import cn.dev33.satoken.secure.BCrypt
import cn.dev33.satoken.stp.StpUtil
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.troForum_server.domain.entity.account.Account
import com.troForum_server.domain.dao.AccountRepository
import com.troForum_server.infrastructure.common.toJsonWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File

/*
* 账号服务层，向下提供json，平层提供Account，通过toJson()进行转化
 */
@Service
class AccountService {
    @Autowired
    private lateinit var accountRepository: AccountRepository

    //把account转进json
    fun Account.toJson(): JSONObject {
        val json = JSONObject()
        json["userId"] = this.userId
        json["userName"] = this.userName
        json["avatarUrl"] = this.avatarUrl
        json["description"] = this.description
        return json
    }

    //获取

    //获取当前用户账号
    fun selectAccount(): JSONObject {
        val account = accountRepository.selectAccountById((StpUtil.getLoginId().toString()))
        try {
            if (account == null) {
                throw Exception("账号不存在")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return account!!.toJson()
    }

    //用id查账号
    fun selectAccountById(userId: String): JSONObject {
        val account =  accountRepository.selectAccountById(userId)
        try {
            if (account == null) {
                throw Exception("账号不存在")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return account!!.toJson()
    }

    //页转json
    fun Page<Account>.toJson(): JSONObject {
        val res = this.toJsonWrapper()
        val data = JSONArray()
        records.forEach {
            val json = JSONObject.toJSON(it) as JSONObject
            data.add(json)
        }
        res["value"] = data
        return res
    }

    //获取用户页(未使用)
    fun getAccountPage(current: Long, size: Long, keyword: String): JSONObject {
        return accountRepository.getAccountPage(Page(current, size), keyword).toJson()
    }

    //获取搜索用户
    fun getSearchAccount(keyword: String): JSONArray {
        val res = JSONArray()
        accountRepository.getSearchAccount(keyword).forEach {
            res.add(it.toJson())
        }
        return res
    }

    //id转用户名
    fun idToAccount(userId: String): Account? {
        val account = accountRepository.selectAccountById(userId)
        try {
            if (account == null) {
                throw Exception("账号不存在")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return account
    }

    //更新

    //用于更新头像
    fun updateAvatar(avatar: MultipartFile): Boolean {
        if (!avatar.isEmpty) {
            try {
                val account = accountRepository.selectAccountById(StpUtil.getLoginId().toString()) ?: throw Exception("账号不存在")
                account.avatarUrl = "/avatar/${StpUtil.getLoginId()}.jpg"
                accountRepository.updateBaseAccount(StpUtil.getLoginId().toString(), account)
                avatar.transferTo(File("/upload/avatar/${StpUtil.getLoginId()}.jpg"))
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
        }
        return true
    }

    //更新描述
    fun updateDescription(description: String): Int {
        val account = accountRepository.selectAccountById(StpUtil.getLoginId().toString()) ?: throw Exception("账号不存在")
        account.description = description
        return accountRepository.updateBaseAccount(StpUtil.getLoginId().toString(), account)
    }

    //更新用户名
    fun updateUserName(userName: String): Int {
        val account = accountRepository.selectAccountById(StpUtil.getLoginId().toString()) ?: throw Exception("账号不存在")
        if (accountRepository.checkingUserName(userName)) {
            throw Exception("用户名重复")
        }
        account.userName = userName
        return accountRepository.updateBaseAccount(StpUtil.getLoginId().toString(), account)
    }

    //更新密码
    fun updatePassword(password: String): Int {
        val account = accountRepository.selectAccountById(StpUtil.getLoginId().toString()) ?: throw Exception("账号不存在")
        account.password = BCrypt.hashpw(password, BCrypt.gensalt())
        return accountRepository.updateBaseAccount(StpUtil.getLoginId().toString(), account)
    }
}