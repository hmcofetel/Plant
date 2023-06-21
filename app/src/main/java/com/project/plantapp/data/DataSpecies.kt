package com.project.plantapp.data

import com.project.plantapp.model.Species
import org.json.JSONObject


object DataSpecies {

    private val jsonString = """
        {
            "Cactus":[
                {"id":"102","author":"Ben Moore","width":4320,"height":3240,"url":"https://unsplash.com/photos/pJILiyPdrXI","download_url":"https://picsum.photos/id/102/4320/3240"}
                ,{"id":"103","author":"Ilham Rahmansyah","width":2592,"height":1936,"url":"https://unsplash.com/photos/DwTZwZYi9Ww","download_url":"https://picsum.photos/id/103/2592/1936"}
                ,{"id":"104","author":"Dyaa Eldin","width":3840,"height":2160,"url":"https://unsplash.com/photos/2fl-ocJ5MOA","download_url":"https://picsum.photos/id/104/3840/2160"}
                ,{"id":"106","author":"Arvee Marie","width":2592,"height":1728,"url":"https://unsplash.com/photos/YnfGtpt2gf4","download_url":"https://picsum.photos/id/106/2592/1728"}
                ,{"id":"107","author":"Lukas Schweizer","width":5000,"height":3333,"url":"https://unsplash.com/photos/9VWOr22LhVI","download_url":"https://picsum.photos/id/107/5000/3333"}
                ,{"id":"108","author":"Florian Klauer","width":2000,"height":1333,"url":"https://unsplash.com/photos/t1mqA3V3-7g","download_url":"https://picsum.photos/id/108/2000/1333"}
                ,{"id":"109","author":"Zwaddi","width":4287,"height":2392,"url":"https://unsplash.com/photos/YvYBOSiBJE8","download_url":"https://picsum.photos/id/109/4287/2392"}
                ,{"id":"110","author":"Kenneth Thewissen","width":5000,"height":3333,"url":"https://unsplash.com/photos/D76DklsG-5U","download_url":"https://picsum.photos/id/110/5000/3333"}
                ,{"id":"111","author":"Gabe Rodriguez","width":4400,"height":2656,"url":"https://unsplash.com/photos/eLUegVAjN7s","download_url":"https://picsum.photos/id/111/4400/2656"}
                ,{"id":"112","author":"Zugr","width":4200,"height":2800,"url":"https://unsplash.com/photos/kmF_Aq8gkp0","download_url":"https://picsum.photos/id/112/4200/2800"}
                ,{"id":"113","author":"Zugr","width":4168,"height":2464,"url":"https://unsplash.com/photos/yZf1quatKCA","download_url":"https://picsum.photos/id/113/4168/2464"}
                ,{"id":"114","author":"Brian Gonzalez","width":3264,"height":2448,"url":"https://unsplash.com/photos/llYg8Ni43fc","download_url":"https://picsum.photos/id/114/3264/2448"}
                ,{"id":"115","author":"Christian Hebell","width":1500,"height":1000,"url":"https://unsplash.com/photos/A6S-q3D67Ss","download_url":"https://picsum.photos/id/115/1500/1000"}
                ,{"id":"116","author":"Anton Sulsky","width":3504,"height":2336,"url":"https://unsplash.com/photos/YcfCXxo7rpc","download_url":"https://picsum.photos/id/116/3504/2336"}
            ],
            
            "Cistus":[
                {"id":"137","author":"Vladimir Kramer","width":4752,"height":3168,"url":"https://unsplash.com/photos/xzZtV9ED5Bs","download_url":"https://picsum.photos/id/137/4752/3168"},{"id":"139","author":"Steve Richey","width":3465,"height":3008,"url":"https://unsplash.com/photos/M-1MRfncLk0","download_url":"https://picsum.photos/id/139/3465/3008"},{"id":"140","author":"Kundan Ramisetti","width":2448,"height":2448,"url":"https://unsplash.com/photos/Acfgb7bc-Bc","download_url":"https://picsum.photos/id/140/2448/2448"},{"id":"141","author":"Greg Shield","width":2048,"height":1365,"url":"https://unsplash.com/photos/v9eNihIWh8k","download_url":"https://picsum.photos/id/141/2048/1365"},{"id":"142","author":"Vadim Sherbakov","width":4272,"height":2848,"url":"https://unsplash.com/photos/KSyemQIWwP8","download_url":"https://picsum.photos/id/142/4272/2848"},{"id":"143","author":"Steve Richey","width":3600,"height":2385,"url":"https://unsplash.com/photos/6xqAK6oAeHA","download_url":"https://picsum.photos/id/143/3600/2385"},{"id":"144","author":"Mouly Kumar","width":4912,"height":2760,"url":"https://unsplash.com/photos/TuOiIpkIea8","download_url":"https://picsum.photos/id/144/4912/2760"},{"id":"145","author":"Lucas Boesche","width":4288,"height":2848,"url":"https://unsplash.com/photos/VkuuTRkcRqw","download_url":"https://picsum.photos/id/145/4288/2848"},{"id":"146","author":"Florian Klauer","width":5000,"height":3333,"url":"https://unsplash.com/photos/GG0jOrmwqtw","download_url":"https://picsum.photos/id/146/5000/3333"},{"id":"147","author":"Kundan Ramisetti","width":2448,"height":2448,"url":"https://unsplash.com/photos/OODWPtfXAF0","download_url":"https://picsum.photos/id/147/2448/2448"}
            ],
            
            "DIERAMA":[
                {"id":"167","author":"petradr","width":2896,"height":1944,"url":"https://unsplash.com/photos/WqK_xV_hbug","download_url":"https://picsum.photos/id/167/2896/1944"},{"id":"168","author":"Joeri Römer","width":1920,"height":1280,"url":"https://unsplash.com/photos/Xne1N4yZuOY","download_url":"https://picsum.photos/id/168/1920/1280"},{"id":"169","author":"Noel Lopez","width":2500,"height":1662,"url":"https://unsplash.com/photos/BjelfpszQDw","download_url":"https://picsum.photos/id/169/2500/1662"},{"id":"170","author":"Noel Lopez","width":2500,"height":1667,"url":"https://unsplash.com/photos/LbS_k_c3BYA","download_url":"https://picsum.photos/id/170/2500/1667"},{"id":"171","author":"Riley Briggs","width":2048,"height":1536,"url":"https://unsplash.com/photos/cSe3oKQ03OQ","download_url":"https://picsum.photos/id/171/2048/1536"},{"id":"172","author":"Aleksi Tappura","width":2000,"height":1325,"url":"https://unsplash.com/photos/TQeX8khR54I","download_url":"https://picsum.photos/id/172/2000/1325"},{"id":"173","author":"Linh Nguyen","width":1200,"height":737,"url":"https://unsplash.com/photos/J8k-gzI0Zy0","download_url":"https://picsum.photos/id/173/1200/737"},{"id":"174","author":"Linh Nguyen","width":1600,"height":589,"url":"https://unsplash.com/photos/YYegjUYIVeg","download_url":"https://picsum.photos/id/174/1600/589"},{"id":"175","author":"petradr","width":2896,"height":1944,"url":"https://unsplash.com/photos/8hgm6mKK04U","download_url":"https://picsum.photos/id/175/2896/1944"}  
            ],
             
            "ECHINACEA":[
                 {"id":"183","author":"müllermarc","width":2316,"height":1544,"url":"https://unsplash.com/photos/k7bQqdUf954","download_url":"https://picsum.photos/id/183/2316/1544"},{"id":"184","author":"Tim de Groot","width":4288,"height":2848,"url":"https://unsplash.com/photos/yNGQ830uFB4","download_url":"https://picsum.photos/id/184/4288/2848"},{"id":"185","author":"Tim de Groot","width":3995,"height":2662,"url":"https://unsplash.com/photos/M_eB1UjE0do","download_url":"https://picsum.photos/id/185/3995/2662"},{"id":"186","author":"Simon Pape","width":2048,"height":1275,"url":"https://unsplash.com/photos/2kc8bigeqEI","download_url":"https://picsum.photos/id/186/2048/1275"},{"id":"187","author":"Andre Koch","width":4000,"height":2667,"url":"https://unsplash.com/photos/oSf8ePoG9NU","download_url":"https://picsum.photos/id/187/4000/2667"}
            ]
       }
    """.trimIndent()


    private val jsonObject = JSONObject(jsonString)

    fun getDataSet(category: String): List<Species>{
        val  dataSpecies = jsonObject.getJSONArray(category)
        val listSpecies = mutableListOf<Species>()

        for (i in 0 until dataSpecies.length()){
            listSpecies += Species(
                dataSpecies.getJSONObject(i).getString("id")
                ,dataSpecies.getJSONObject(i).getString("author")
                ,dataSpecies.getJSONObject(i).getString("width")
                ,dataSpecies.getJSONObject(i).getString("height")
                ,dataSpecies.getJSONObject(i).getString("url")
                ,dataSpecies.getJSONObject(i).getString("download_url")
                ,false
            )
        }
        return listSpecies
    }

    fun getCategoryList():ArrayList<String> {
        val result  =  ArrayList<String>()
        for (key: String in iterate(jsonObject.keys())) {
            result.add(key)
        }
        return  result
    }

    private fun <T> iterate(i: Iterator<T>): Iterable<T> {
        return object : Iterable<T> {
            override fun iterator(): Iterator<T> {
                return i
            }
        }
    }

}