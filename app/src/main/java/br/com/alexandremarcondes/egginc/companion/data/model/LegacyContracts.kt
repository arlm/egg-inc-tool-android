package br.com.alexandremarcondes.egginc.companion.data.model

import android.content.Context
import br.com.alexandremarcondes.egginc.companion.util.isInThePast
import ei.Ei
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

class LegacyContracts() {
    private lateinit var contracts: List<Contract>

    constructor(context: Context) : this() {
        val inputStream = context.assets.open("legacy-contracts.xml")
        val dbFactory = DocumentBuilderFactory.newInstance()
        val dBuilder = dbFactory.newDocumentBuilder()
        val xmlInput = InputSource(inputStream)
        val doc = dBuilder.parse(xmlInput)
        val xpFactory = XPathFactory.newInstance()
        val xPath = xpFactory.newXPath()
        val xpath = "/contracts/contract"

        val elementNodeList = xPath.evaluate(xpath, doc, XPathConstants.NODESET) as NodeList

        val list: MutableList<Contract> = ArrayList()

        for (index in 0 until elementNodeList.length) {
            val item = elementNodeList.item(index)
            var id = ""
            var name = ""
            var expiration = 0.0

            for (attrIndex in 0 until item.attributes.length) {
                val attr = item.attributes.item(attrIndex)

                when (attr.nodeName) {
                    "id" -> id = item.attributes.getNamedItem("id").nodeValue
                    "name" -> name = item.attributes.getNamedItem("name").nodeValue
                    "expiration" -> expiration =
                        item.attributes.getNamedItem("expiration").nodeValue.toDouble()
                }
            }

            val contract = Contract(
                identifier = id,
                description = item.textContent,
                name = name,
                expirationTime = expiration
            )
            list.add(contract)
        }

        contracts = list
    }

    constructor(contracts: Iterable<Contract>): this() {
        val list = ArrayList<Contract>()
        list.addAll(contracts)

        this.contracts = list
    }

    val size: Int
        get () = contracts.size

    fun missingContracts(userContracts: Iterable<Contract>): List<Contract> =
        contracts.filter { legacyContract ->
            !userContracts.any { contract -> legacyContract.identifier == contract.identifier }
        }

    fun missingContracts(userContracts: List<Ei.LocalContract>): List<Contract> =
        contracts.filter { legacyContract ->
            !userContracts.any { contract -> legacyContract.identifier == contract.contract.identifier }
        }

    fun doneContracts(userContracts: Iterable<Contract>): List<Contract> =
        contracts.filter { legacyContract ->
            userContracts.any { contract -> legacyContract.identifier == contract.identifier }
        }

    fun doneContracts(userContracts: List<Ei.LocalContract>): List<Contract> =
        contracts.filter { legacyContract ->
            userContracts.any { contract -> legacyContract.identifier == contract.contract.identifier }
        }

    fun currentContracts(): List<Contract> =
        contracts.filter { legacyContract -> !legacyContract.expirationTime.isInThePast() }

    fun archivedContracts(): List<Contract> =
        contracts.filter { legacyContract -> legacyContract.expirationTime.isInThePast() }

    fun currentDoneContracts(userContracts: Iterable<Contract>): List<Contract> =
        contracts.filter { legacyContract -> !legacyContract.expirationTime.isInThePast() &&
                userContracts.any { contract -> legacyContract.identifier == contract.identifier }
        }

    fun currentDoneContracts(userContracts: List<Ei.LocalContract>): List<Contract> =
        contracts.filter { legacyContract -> !legacyContract.expirationTime.isInThePast() &&
                userContracts.any { contract -> legacyContract.identifier == contract.contract.identifier }
        }

    fun currentMissingContracts(userContracts: Iterable<Contract>): List<Contract> =
        contracts.filter { legacyContract -> !legacyContract.expirationTime.isInThePast() &&
                !userContracts.any { contract -> legacyContract.identifier == contract.identifier }
        }

    fun currentMissingContracts(userContracts: List<Ei.LocalContract>): List<Contract> =
        contracts.filter { legacyContract -> !legacyContract.expirationTime.isInThePast() &&
                !userContracts.any { contract -> legacyContract.identifier == contract.contract.identifier }
        }
}