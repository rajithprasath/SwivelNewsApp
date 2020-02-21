package com.rajith.codetest.di

import com.rajith.codetest.di.modules.NewsModule
import com.rajith.codetest.di.modules.RepositoryModule
import com.rajith.codetest.di.scope.FeatureScope
import com.rajith.codetest.view.NewsCategoryFragment
import com.rajith.codetest.view.ProfileFragment
import com.rajith.codetest.view.TopHeadlineFragment
import dagger.Component


@FeatureScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [
        NewsModule::class,
        RepositoryModule::class
    ]
)
interface NewsComponent {
    fun inject(topHeadlineFragment: TopHeadlineFragment)
    fun inject(newsCategoryFragment: NewsCategoryFragment)
    fun inject(profileFragment: ProfileFragment)
}
