package com.example.marketplace.domain.model

class FakeData {

    private val item1 = DataModelInterface.ProductDetail(
        DataModelInterface.ProductInfo(
        productId = 1,
        itemName = "Denver",
        price = 7500.0,
        currency = "KWD",
        thumbnail = "https://images.pexels.com/photos/1996333/pexels-photo-1996333.jpeg?cs=srgb&dl=pexels-helena-lopes-1996333.jpg&fm=jpg",
        images = listOf(
            "https://www.aspca.org/sites/default/files/blog_horse-code-part-one_011922_main.jpg",
            "https://www.bluecross.org.uk/sites/default/files/d8/assets/images/111432lpr.jpg"
        ),
        description = "test",
            isAvailable = true,
            category = ProductCategory.trading,
            expiredDate = "2022-07-10T08:14:00.000Z",
            country = "kuwait",
        ),
        place =DataModelInterface.Place(latitude = 30.0,
            longitude = 31.2,
            address = "gamal abd elnaser street "
        ),
        seller = DataModelInterface.Owner(name = "Rawan", phoneNumber = 1550474808, email = "rbahnasy98@gmail.com",profileImg = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f9/Dylan_O%27Brien_in_2017_by_Gage_Skidmore.jpg/800px-Dylan_O%27Brien_in_2017_by_Gage_Skidmore.jpg"),
        horseInfo = DataModelInterface.HorseInfo(
            name = "Denver",
            color = "White",
            gender = "Gender",
            breed = "Horse Breed",
            strain = "Horse Strain"
        ),
    )
    private val item2 = DataModelInterface.ProductDetail(
        DataModelInterface.ProductInfo(
        productId = 2,
        itemName = "Laredo",
        price = 5000.0,
        currency = "USD",
        thumbnail = "https://images.unsplash.com/photo-1598974357801-cbca100e65d3?ixlib=rb-1.2.1&w=1080&fit=max&q=80&fm=jpg&crop=entropy&cs=tinysrgb",
        images = listOf("https://img.freepik.com/free-photo/horse-silhouette-against-warm-light_23-2149334095.jpg?w=2000"),
        description = "test",
            isAvailable = true,
            category = ProductCategory.trading,
            expiredDate = "2022-07-10T08:14:00.000Z",
            country = "kuwait",
        ),
        seller = DataModelInterface.Owner(name = "Rawan", phoneNumber = 1550474808, email = "rbahnasy98@gmail.com"),
        horseInfo = DataModelInterface.HorseInfo(
            name = "Laredo",
            color = "Brown",
            sexType = "Male",
            gender = "Gender",
            DOB = "16 / 10 / 2021",
            breed = "Horse Breed",
            strain = "Horse Strain",
        ),


    )
    private val item3 = DataModelInterface.ProductDetail(
        DataModelInterface.ProductInfo(
        productId = 3,
        itemName = "Horse Chair seat",
        price = 240.0,
        currency = "USD",
        thumbnail = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSNa2YHs2_xZt_QU6mydXVkB1XgEnd43EWZnvCqq1M_a8CJar8C0SpOsBWVvZEyWHIIs6A&usqp=CAU",
        images = listOf("https://keyassets.timeincuk.net/inspirewp/live/wp-content/uploads/sites/14/2022/01/HH_Saddle_Fitting_20101016_347-920x518.jpg"),
        description = "test",
            isAvailable = true,
            category = ProductCategory.usedEqu,
            expiredDate = "2022-07-10T08:14:00.000Z",
            country = "kuwait",
        ),
        place = DataModelInterface.Place(
            latitude = 40.20,
            longitude = 19.68 ,
            address = "gamal abd elnaser street "
        ),
        seller = DataModelInterface.Owner(name = "Rawan", phoneNumber = 1550474808, email = "rbahnasy98@gmail.com"),
    )
    private val item4 = DataModelInterface.ProductDetail(
        DataModelInterface.ProductInfo(
        productId = 4,
        itemName = "Denver",
        price = 7500.0,
        currency = "KWD",
        thumbnail = "https://images.pexels.com/photos/1996333/pexels-photo-1996333.jpeg?cs=srgb&dl=pexels-helena-lopes-1996333.jpg&fm=jpg",
        images = listOf(
            "https://www.aspca.org/sites/default/files/blog_horse-code-part-one_011922_main.jpg",
            "https://www.bluecross.org.uk/sites/default/files/d8/assets/images/111432lpr.jpg"
        ),
        description = "test",
            isAvailable = false,
            category = ProductCategory.trading,
            expiredDate = "2022-07-10T08:14:00.000Z",
            country = "kuwait",
            ),
        place = DataModelInterface.Place(
            latitude = 42.9096,
            longitude = 25.2276,
            address = "gamal abd elnaser street "),
        seller = DataModelInterface.Owner(name = "Rawan", phoneNumber = 0, email = "rbahnasy98@gmail.com"),
        horseInfo = DataModelInterface.HorseInfo(
            name = "Denver",
            color = "White",
            sexType = "Male",
            gender = "Gender",
            DOB = "12 / 1 / 2021",
            breed = "Horse Breed",
            strain = "Horse Strain"
        ),
    )
    private val item5 = DataModelInterface.ProductDetail(
        DataModelInterface.ProductInfo(
        productId = 5,
        itemName = "Laredo",
        price = 5000.0,
        currency = "USD",
        thumbnail = "https://images.unsplash.com/photo-1598974357801-cbca100e65d3?ixlib=rb-1.2.1&w=1080&fit=max&q=80&fm=jpg&crop=entropy&cs=tinysrgb",
        images = listOf("https://www.thesprucepets.com/thmb/KYaXBSM013GnZ2jEZJnX4a9oIsU=/3865x2174/smart/filters:no_upscale()/horse-galloping-in-grass-688899769-587673275f9b584db3a44cdf.jpg"),
        description = "test",
            isAvailable = true,
            category = ProductCategory.trading,
            expiredDate = "2022-07-10T08:14:00.000Z",
            country = "kuwait",

            ),
        place = DataModelInterface.Place(
            latitude = 40.20,
            longitude = 19.68,address = "gamal abd elnaser street "),
        seller = DataModelInterface.Owner(name = "Rawan", phoneNumber = 1550474808, email = "rbahnasy98@gmail.com"),
        horseInfo = DataModelInterface.HorseInfo(
            name = "Laredo",
            color = "Brown",
            sexType = "Male",
            gender = "Gender",
            DOB = "16 / 10 / 2021",
            breed = "Horse Breed",
            strain = "Horse Strain"
        ),


    )
    private val item6 = DataModelInterface.ProductDetail(
        DataModelInterface.ProductInfo(
        productId = 6,
        itemName = "Horse Chair seat",
        price = 240.0,
        currency = "USD",
        thumbnail = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSNa2YHs2_xZt_QU6mydXVkB1XgEnd43EWZnvCqq1M_a8CJar8C0SpOsBWVvZEyWHIIs6A&usqp=CAU",
        images = listOf("https://keyassets.timeincuk.net/inspirewp/live/wp-content/uploads/sites/14/2022/01/HH_Saddle_Fitting_20101016_347-920x518.jpg"),
        description = "test",
            isAvailable = true,
            category = ProductCategory.usedEqu,
            expiredDate = "2022-07-10T08:14:00.000Z",
            country = "kuwait",
        ),
        place = DataModelInterface.Place(
            latitude = 40.20,
            longitude = 19.68,
            address = "gamal abd elnaser street "
        ),
        seller = DataModelInterface.Owner(
            name = "Rawan",
            phoneNumber = 1550474808,
            email = "rbahnasy98@gmail.com"
        ),

    )
        val response: List<DataModelInterface.ProductDetail> = listOf(
            item1,
            item2,
            item3,
            item4,
            item5,
            item6,
            item1,
            item2,
            item3,
            item4,
            item5,
            item6,
            item1,
            item2,
            item3,
            item4,
            item5,
            item6
        )
        val responseForList: List<DataModelInterface.ProductInfo> = listOfNotNull(
            item1.productInfo,
            item2.productInfo,
            item3.productInfo,
            item4.productInfo,
            item5.productInfo,
            item6.productInfo,
            item1.productInfo,
            item2.productInfo,
            item3.productInfo,
            item4.productInfo,
            item5.productInfo,
            item6.productInfo,
            item1.productInfo,
            item2.productInfo,
            item3.productInfo,
            item4.productInfo,
            item5.productInfo,
            item6.productInfo
        )
        val horseTradingList: List<DataModelInterface.ProductInfo> =
            listOfNotNull(item1.productInfo, item2.productInfo, item4.productInfo)
        val usedEquipmentList: List<DataModelInterface.ProductInfo> = listOfNotNull(item3.productInfo)

    }

