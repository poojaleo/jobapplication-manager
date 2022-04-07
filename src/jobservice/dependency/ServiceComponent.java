package jobservice.dependency;

import dagger.Component;
import jobservice.activity.*;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DaoModule.class})
public interface ServiceComponent {
    GetUserActivity provideGetUserActivity();
    CreateUserActivity provideCreateUserActivity();
    UpdateUserActivity provideUpdateUserActivity();
    CreateJobApplicationActivity provideCreateJobApplicationActivity();
    UpdateJobApplicationActivity provideUpdateJobApplicationActivity();
    GetJobApplicationActivity provideGetJobApplicationActivity();
    GetAllJobApplicationsActivity provideGetAllJobApplicationsActivity();
    DeleteJobApplicationActivity provideDeleteJobApplicationActivity();
    CreateQuestionActivity provideCreateQuestionActivity();
    GetQuestionActivity provideGetQuestionActivity();
    UpdateQuestionActivity provideUpdateQuestionActivity();
    DeleteQuestionActivity provideDeleteQuestionActivity();
    GetAllQuestionsActivity provideGetAllQuestionsActivity();
    AddQuestionToJobApplicationActivity provideAddQuestionToJobApplicationActivity();
}
