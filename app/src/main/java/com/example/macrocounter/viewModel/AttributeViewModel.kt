package com.example.macrocounter.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.macrocounter.model.entity.CartItemEntity
import com.example.macrocounter.model.entity.CartItemForViewEntity
import com.example.macrocounter.model.entity.FoodEntity
import androidx.compose.runtime.getValue
import com.example.macrocounter.model.service.CategoryService
import com.example.macrocounter.model.service.OrderService

class AttributeViewModel : ViewModel() {

    private val orderService = OrderService.instance()

    var isDialogShown by mutableStateOf(false)
        private set

    var selectedfood by mutableStateOf<FoodEntity?>(null)

    var shoppingCart by mutableStateOf(emptyArray<CartItemForViewEntity>())
        private set

//        var shoppingCart by mutableListOf<CartItemForViewEntity>()
//        private set

    fun onItemClick(food: FoodEntity){
        selectedfood = food
        isDialogShown = true
    }

    fun onDismissDialog(){
        isDialogShown = false
    }

    private fun isEqual(item1: CartItemForViewEntity, item2: CartItemEntity): Boolean{
        if(item1.food.id != item2.food.id)  return false
        else if(item1.spec?.size != item2.spec?.size )   return false
        else
            item1.spec?.forEachIndexed { i, e ->
                if ((item1.spec[i].attribute != item2.spec!![i].attribute) ||
                    ((item1.spec[i].attribute == item2.spec!![i].attribute) && (item1.spec[i].optioned
                            != item2.spec[i].optioned))
                )
                    return false
            }
        return true
    }

    fun addToCart(item: CartItemEntity){
        var hasEqual = false
        shoppingCart.forEach { e-> if (isEqual(e, item)){
                e.quantity += 1
                hasEqual = true
            }
        }
        if(!hasEqual){
            val tmpList = mutableListOf<CartItemForViewEntity>()
            val newItem = CartItemForViewEntity(food = item.food, quantity = 1, spec = item.spec)
            tmpList.add(newItem)
            shoppingCart += tmpList.toTypedArray()
        }

    }

    fun decreaseItem(item: CartItemForViewEntity){
        shoppingCart.forEach { e-> if (e.food.id == item.food.id){
            if(e.quantity > 1){
                e.quantity -= 1

            }else{
                //remove
            }

            }
        }
    }

    fun removeItem(item: CartItemForViewEntity){
        shoppingCart.forEach { e-> if (e.food.id == item.food.id){
            if(e.quantity > 1){
                e.quantity -= 1

            }else{
                //remove
            }

        }
        }
    }

    suspend fun createTableOrder(token: String, spaceId: String, table: String) {
        val tmpList = mutableListOf<CartItemEntity>()

        shoppingCart.forEach {
            for (i in 0..it.quantity) {
                tmpList.add(CartItemEntity(food = it.food, spec = it.spec))
            }
        }
        val createOrderRequest = OrderService.CreateOrderRequest(foods = tmpList, tableLabel = table)
        val res = orderService.createOrder(token = "Bearer $token", spaceId = spaceId, createOrderRequest = createOrderRequest)
        if (res != null) {
//            val tmpList = mutableListOf<FoodEntity>()
//            tmpList.addAll(res)



        }
        shoppingCart = emptyArray()
    }
}

