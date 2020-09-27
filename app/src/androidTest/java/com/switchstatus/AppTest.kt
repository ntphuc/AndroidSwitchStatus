package com.switchstatus
import com.switchstatus.di.DaggerTestAppComponent

class AppTest: App() {
    override fun initDagger() {
        DaggerTestAppComponent.builder().application(this).build().inject(this)
    }
}
