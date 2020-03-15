package com.example.simplifiedinstagramstorymodule.core.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.simplifiedinstagramstorymodule.ui.story.StoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(StoryViewModel::class)
    abstract fun bindsStoryViewModel(storyViewModel: StoryViewModel): ViewModel


}