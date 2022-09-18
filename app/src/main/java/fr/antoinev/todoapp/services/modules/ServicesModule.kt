package fr.antoinev.todoapp.services.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import fr.antoinev.todoapp.models.Todo
import fr.antoinev.todoapp.models.Topic
import fr.antoinev.todoapp.services.AuthService
import fr.antoinev.todoapp.services.DaoService
import fr.antoinev.todoapp.services.LogService
import fr.antoinev.todoapp.services.TodoDaoService
import fr.antoinev.todoapp.services.dao.TodoDaoServiceImpl
import fr.antoinev.todoapp.services.dao.TopicDaoServiceImpl
import fr.antoinev.todoapp.services.impls.AuthServiceImpl
import fr.antoinev.todoapp.services.impls.LogServiceImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class ServicesModule {

    @Binds abstract fun provideAuthService(impl: AuthServiceImpl): AuthService
    @Binds abstract fun provideLogService(impl: LogServiceImpl): LogService
    @Binds abstract fun provideTopicDaoService(impl: TopicDaoServiceImpl): DaoService<Topic>
    @Binds abstract fun provideTodoDaoService(impl: TodoDaoServiceImpl): TodoDaoService

}