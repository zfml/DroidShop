package com.schoolproject.droidshop.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.schoolproject.droidshop.data.local.dao.CartDao
import com.schoolproject.droidshop.data.local.dao.FavouriteDao
import com.schoolproject.droidshop.data.local.database.DroidShopDatabase
import com.schoolproject.droidshop.data.remote.auth.FirebaseAuthService
import com.schoolproject.droidshop.data.remote.firestore.CategoryService
import com.schoolproject.droidshop.data.remote.firestore.ProductService
import com.schoolproject.droidshop.data.repository.AuthRepositoryImpl
import com.schoolproject.droidshop.data.repository.FavouriteRepositoryImpl
import com.schoolproject.droidshop.data.repository.ProductRepositoryImpl
import com.schoolproject.droidshop.domain.repository.AuthRepository
import com.schoolproject.droidshop.domain.repository.FavouriteRepository
import com.schoolproject.droidshop.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()


    @Provides
    @Singleton
    fun provideDatabase(app: Application): DroidShopDatabase {
        return Room.databaseBuilder(
            app,
            DroidShopDatabase::class.java,
            "droid_shop.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCartDao(db: DroidShopDatabase): CartDao {
        return db.cartDao()
    }

    @Provides
    @Singleton
    fun provideFavouriteDao(db: DroidShopDatabase): FavouriteDao {
        return db.favouriteDao()
    }


//    @Provides
//    @Singleton
//    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
//        Room.databaseBuilder(context, AppDatabase::class.java, "app_db").build()
//
//    @Provides
//    fun provideFavoriteDao(db: AppDatabase): FavoriteDao = db.favoriteDao()

    @Provides
    @Singleton
    fun provideAuthService(auth: FirebaseAuth): FirebaseAuthService =
        FirebaseAuthService(auth)

    @Provides
    @Singleton
    fun provideCategoryService(fs: FirebaseFirestore): CategoryService =
        CategoryService(fs)

    @Provides
    @Singleton
    fun provideProductService(fs: FirebaseFirestore): ProductService =
        ProductService(fs)


    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuthService: FirebaseAuthService
    ): AuthRepository = AuthRepositoryImpl(
        firebaseAuthService
    )




    @Singleton
    @Provides
    fun provideProductRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore,
        cartDao: CartDao,
        favouriteDao: FavouriteDao
    ): ProductRepository = ProductRepositoryImpl(firestore,cartDao,favouriteDao,firebaseAuth)

    @Singleton
    @Provides
    fun provideFavouriteRepository(
        favouriteDao: FavouriteDao
    ): FavouriteRepository = FavouriteRepositoryImpl(favouriteDao)

//    @Provides
//    @Singleton
//    fun provideProductRepository(
//        authService: FirebaseAuthService,
//        categoryService: CategoryService,
//        productService: ProductService,
//        favoriteDao: FavoriteDao
//    ): ProductRepository = DefaultProductRepository(
//        authService, categoryService, productService, favoriteDao
//    )
}
