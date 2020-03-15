package com.example.simplifiedinstagramstorymodule.core.di

import com.example.simplifiedinstagramstorymodule.core.StoryModuleApplication
import com.example.simplifiedinstagramstorymodule.core.di.viewmodel.ViewModelModule
import com.example.simplifiedinstagramstorymodule.ui.story.StoryFragment
import dagger.Component
import javax.inject.Singleton

@Suppress("TooManyFunctions", "ComplexInterface")
@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: StoryModuleApplication)
    fun inject(storyFragment: StoryFragment)
}