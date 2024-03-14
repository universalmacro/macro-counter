package com.example.macrocounter.viewModel

import android.content.Context
import android.util.Log
import com.example.macrocounter.model.entity.TableEntity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.macrocounter.model.entity.CartItemEntity
import com.example.macrocounter.model.entity.OrderEntity
import com.example.macrocounter.model.service.OrderService
import com.example.macrocounter.model.service.TableService

class TableViewModel(context: Context) : ViewModel() {

    private val tableService = TableService.instance()
    private val orderService = OrderService.instance()


    var list by mutableStateOf(emptyArray<TableEntity>())
        private set

    var orderList by mutableStateOf(emptyArray<OrderEntity>())
        private set

    var selectedOrder by mutableStateOf<OrderEntity?>(null)
    var selectedOrderShow by mutableStateOf<OrderEntity?>(null)

//    val selectedOrderShow = MutableLiveData<SnapshotStateList<OrderEntity?>>()
var selectedOrderFoods = mutableStateListOf<CartItemEntity?>()
    var deleteOrderFoods = mutableStateListOf<CartItemEntity?>()


//    var selectedOrderShow by MutableLiveData<SnapshotStateList<OrderEntity?>>(null)


    var isDialogShown by mutableStateOf(false)

    var error by mutableStateOf("")
        private set

    fun onDismissDialog(){
        isDialogShown = false
    }

    //第一页文章列表数据是否加载完成
    var listLoaded by mutableStateOf(false)
        private set

    var selectedTable: TableEntity? by mutableStateOf(null)

    //是否还有更多
    private var hasMore = false

    fun deleteFood(index: Int){
//        selectedOrderShow?.foods?.removeAt(index)
//        selectedOrderFoods = selectedOrderShow?.foods
        deleteOrderFoods.add(selectedOrderFoods[index])

        selectedOrderFoods.removeAt(index)

    }

    suspend fun fetchTableList(token: String, spaceId: String) {
        val res = tableService.list(token = "Bearer $token", spaceId = spaceId)
        if (res != null) {
            val tmpList = mutableListOf<TableEntity>()
            tmpList.addAll(res)

            //是否还有更多数据
            list = tmpList.toTypedArray()
            listLoaded = true
        }
    }


    suspend fun fetchOrderList(token: String, spaceId: String, statuses: List<String>?) {
        try{
            val res = orderService.listOrder(token = "Bearer $token", spaceId = spaceId, statuses=statuses)
            if (res != null) {
                val tmpList = mutableListOf<OrderEntity>()
                tmpList.addAll(res)

                orderList = tmpList.toTypedArray()

            }

        } catch (exception: Exception) {
            // handle errors
            Log.d("=====Exception", "${exception} ")
            error = exception.message.toString()
        }
    }

    suspend fun cancelFromOrder(token: String, orderId: String) {

        val cancelOrderRequest = OrderService.OrderRequest(foods = deleteOrderFoods.toList())

        try{

            orderService.cancelOrder(token = "Bearer $token", orderId = orderId, cancelOrderRequest = cancelOrderRequest)

        } catch (exception: Exception) {
            // handle errors
            Log.d("=====Exception", "$exception ")
            error = exception.message.toString()
        }

//        shoppingCart = emptyArray()
    }


    suspend fun createBill(token: String, createBillRequest: OrderService.CreateBillRequest) {
        try{
            orderService.createBill(token = "Bearer $token", createBillRequest = createBillRequest)
        } catch (exception: Exception) {
            // handle errors
            Log.d("=====Exception", "$exception ")
            error = exception.message.toString()
        }
    }

    suspend fun printBill(token: String, printBillRequest: OrderService.CreateBillRequest) {
        try{
            orderService.printBill(token = "Bearer $token", printBillRequest = printBillRequest)
        } catch (exception: Exception) {
            // handle errors
            Log.d("=====Exception", "$exception ")
            error = exception.message.toString()
        }
    }


    private var tableEntity: TableEntity? = null


}