package com.schoolproject.droidshop.data.mappers

import com.google.firebase.auth.FirebaseUser
import com.schoolproject.droidshop.data.model.CartDto
import com.schoolproject.droidshop.data.model.CartEntity
import com.schoolproject.droidshop.data.model.CategoryDto
import com.schoolproject.droidshop.data.model.FavouriteEntity
import com.schoolproject.droidshop.data.model.OrderDto
import com.schoolproject.droidshop.data.model.ProductDto
import com.schoolproject.droidshop.domain.model.Cart
import com.schoolproject.droidshop.domain.model.Category
import com.schoolproject.droidshop.domain.model.Favourite
import com.schoolproject.droidshop.domain.model.Order
import com.schoolproject.droidshop.domain.model.Product
import com.schoolproject.droidshop.domain.model.User

fun CategoryDto.toCategory(): Category = Category(
     id = id,
     name = name,
     imageUrl = imageUrl
)

fun Category.toCategoryDto(): CategoryDto = CategoryDto(
    name = name,
    imageUrl = imageUrl
)


fun ProductDto.toProduct(): Product = Product(
    id = id,
    name = name,
    description = description,
    price = price,
    imageUrl = imageUrl,
    categoryId = categoryId
)

fun Product.toProductDto(): ProductDto = ProductDto(
    name = name,
    description = description,
    price = price,
    imageUrl = imageUrl,
    categoryId = categoryId
)

fun FirebaseUser.toUser(): User = User(
    uid = uid,
    email = email,
    displayName = displayName
)


fun CartEntity.toCart(): Cart = Cart(
    id = id,
    productId = productId,
    productName = productName,
    productDescription = productDescription,
    productImage = productImage,
    productQuantity = productQuantity,
    productPrice = productPrice,
    isChecked = isChecked,
)


fun Cart.toCartEntity(): CartEntity = CartEntity(
    productId = productId,
    productName = productName,
    productDescription = productDescription,
    productImage = productImage,
    productQuantity = productQuantity,
    productPrice = productPrice,
    isChecked = isChecked,
)



fun FavouriteEntity.toFavourite(): Favourite = Favourite(
    id = id,
    productId = productId,
    productName = productName,
    productPrice = productPrice,
    productImage = productImage,
    productCategory = productCategory,
    productQuantity = productQuantity
)

fun Favourite.toFavouriteEntity(): FavouriteEntity = FavouriteEntity(
    productId = productId,
    productName = productName,
    productPrice = productPrice,
    productImage = productImage,
    productCategory = productCategory,
    productQuantity = productQuantity
)

fun Cart.toCartDto(): CartDto = CartDto(
    productId = productId,
    productName = productName,
    productDescription = productDescription,
    productImage = productImage,
    productQuantity = productQuantity,
    productPrice = productPrice,
    isChecked = isChecked
)

fun CartDto.toCart(): Cart = Cart(
    productId = productId,
    productName = productName,
    productDescription = productDescription,
    productImage = productImage,
    productQuantity = productQuantity,
    productPrice = productPrice,
    isChecked = isChecked
)

fun Order.toOrderDto(): OrderDto = OrderDto(
    userId = userId,
    items = items.map { it.toCartDto() },
    address = address,
    paymentMethod = paymentMethod,
    totalAmount = totalAmount,
    timestamp = timestamp
)

fun OrderDto.toOrder(): Order = Order(
    userId = userId,
    items = items.map { it.toCart() },
    address = address,
    paymentMethod = paymentMethod,
    totalAmount = totalAmount,
    timestamp = timestamp
)
