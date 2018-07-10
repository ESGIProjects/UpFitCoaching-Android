package com.mycoaching.mycoaching.Api;

import android.util.Log;

import com.mycoaching.mycoaching.Models.Realm.Message;
import com.mycoaching.mycoaching.Models.Retrofit.Appraisal;
import com.mycoaching.mycoaching.Models.Retrofit.Event;
import com.mycoaching.mycoaching.Models.Retrofit.Measurement;
import com.mycoaching.mycoaching.Models.Retrofit.Post;
import com.mycoaching.mycoaching.Models.Retrofit.Prescription;
import com.mycoaching.mycoaching.Models.Retrofit.Test;
import com.mycoaching.mycoaching.Models.Retrofit.Thread;
import com.mycoaching.mycoaching.Models.Retrofit.UserRetrofit;

import java.util.List;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kevin on 08/03/2018.
 */


public class ApiCall {

    /**
     * Endpoints for authentication
     */

    public static void signIn(String mail, String password, final ServiceResultListener srl) {
        ApiUtils.getApiInstance().signIn(mail, password).enqueue(new Callback<UserRetrofit>() {
            @Override
            public void onResponse(Call<UserRetrofit> call, Response<UserRetrofit> response) {
                ApiResults sir = new ApiResults();
                sir.setResponseCode(response.code());
                sir.setUr(response.body());
                srl.onResult(sir);
            }

            @Override
            public void onFailure(Call<UserRetrofit> call, Throwable t) {
                ApiResults sir = new ApiResults();
                sir.setException(t);
                srl.onResult(sir);
            }
        });
    }

    public static void signUp(String type, String mail, String password, String firstName, String lastName,
                              String sex, String birthDate, String city, String address, String phoneNumber,
                              final ServiceResultListener srl) {
        ApiUtils.getApiInstance().signUp(type, mail, password, firstName, lastName, sex, birthDate, city, address, phoneNumber)
                .enqueue(new Callback<UserRetrofit>() {
                    @Override
                    public void onResponse(Call<UserRetrofit> call, Response<UserRetrofit> response) {
                        ApiResults sir = new ApiResults();
                        sir.setResponseCode(response.code());
                        sir.setUr(response.body());
                        srl.onResult(sir);
                    }

                    @Override
                    public void onFailure(Call<UserRetrofit> call, Throwable t) {
                        ApiResults sir = new ApiResults();
                        sir.setException(t);
                        srl.onResult(sir);
                    }
                });
    }

    public static void checkMail(String mail, final ServiceResultListener srl) {
        ApiUtils.getApiInstance().checkMail(mail).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                ApiResults sir = new ApiResults();
                sir.setResponseCode(response.code());
                srl.onResult(sir);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ApiResults sir = new ApiResults();
                sir.setException(t);
                srl.onResult(sir);
            }
        });
    }

    /**
     * Endpoint for message
     */

    public static void getConversation(int id, final ServiceResultListener srl) {
        ApiUtils.getApiInstance().getConversation(id).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                ApiResults sir = new ApiResults();
                sir.setResponseCode(response.code());
                sir.setListMessage(response.body());
                srl.onResult(sir);
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                ApiResults sir = new ApiResults();
                sir.setException(t);
                srl.onResult(sir);
            }
        });
    }

    /**
     * Endpoints for threads and posts
     */

    public static void getThreads(int id, final ServiceResultListener srl) {
        ApiUtils.getApiInstance().getThreads(id).enqueue(new Callback<List<Thread>>() {
            @Override
            public void onResponse(Call<List<Thread>> call, Response<List<Thread>> response) {
                ApiResults sir = new ApiResults();
                sir.setResponseCode(response.code());
                sir.setListThread(response.body());
                srl.onResult(sir);
            }

            @Override
            public void onFailure(Call<List<Thread>> call, Throwable t) {
                ApiResults sir = new ApiResults();
                sir.setException(t);
                srl.onResult(sir);
            }
        });
    }

    public static void createThread(String title, String date, String content, String forumId, String userId, final ServiceResultListener srl) {
        ApiUtils.getApiInstance().createThread(title, date, content, forumId, userId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                ApiResults sir = new ApiResults();
                sir.setResponseCode(response.code());
                srl.onResult(sir);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ApiResults sir = new ApiResults();
                sir.setException(t);
                srl.onResult(sir);
            }
        });
        {

        }
    }

    public static void getPosts(int id, final ServiceResultListener srl) {
        ApiUtils.getApiInstance().getPosts(id).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                ApiResults sir = new ApiResults();
                sir.setResponseCode(response.code());
                sir.setListPost(response.body());
                srl.onResult(sir);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                ApiResults sir = new ApiResults();
                sir.setException(t);
                srl.onResult(sir);
            }
        });
    }

    public static void sendPost(String threadId, String date, String content, String userId, final ServiceResultListener srl) {
        ApiUtils.getApiInstance().sendPost(threadId, date, content, userId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                ApiResults sir = new ApiResults();
                sir.setResponseCode(response.code());
                srl.onResult(sir);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ApiResults sir = new ApiResults();
                sir.setException(t);
                srl.onResult(sir);
            }
        });
    }

    /**
     * Endpoints for events
     */

    public static void getEvents(int id, final ServiceResultListener srl) {
        ApiUtils.getApiInstance().getEvents(id).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                ApiResults sir = new ApiResults();
                sir.setResponseCode(response.code());
                sir.setListEvent(response.body());
                srl.onResult(sir);
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                ApiResults sir = new ApiResults();
                sir.setException(t);
                srl.onResult(sir);
            }
        });
    }

    public static void addEvent (String name, String type, String firstUser, String secondUser,
                                 String start, String end, String created, String createdBy,
                                 final ServiceResultListener srl) {
        ApiUtils.getApiInstance().addEvent(name,type,firstUser,secondUser,start,
                end,created,createdBy).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                ApiResults sir = new ApiResults();
                sir.setResponseCode(response.code());
                srl.onResult(sir);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ApiResults sir = new ApiResults();
                sir.setException(t);
                srl.onResult(sir);
            }
        });
    }

    public static void updateEvent(String eventId, String name, String type, String start, String end,
            String updated, String updatedBy, final ServiceResultListener srl) {
        ApiUtils.getApiInstance().updateEvent(eventId,name,type,start,end,updated,
                updatedBy).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                ApiResults sir = new ApiResults();
                sir.setResponseCode(response.code());
                srl.onResult(sir);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ApiResults sir = new ApiResults();
                sir.setException(t);
                srl.onResult(sir);
            }
        });
    }

    public static void deleteEvent(String eventId, String userId, final ServiceResultListener srl) {
        ApiUtils.getApiInstance().deleteEvent(eventId, userId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                ApiResults sir = new ApiResults();
                Log.i("REQUEST : ",response.headers().toString());
                sir.setResponseCode(response.code());
                srl.onResult(sir);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ApiResults sir = new ApiResults();
                sir.setException(t);
                srl.onResult(sir);
            }
        });
    }

    /**
     * Endpoint for firebase token
     */

    public static void putToken(String userId, String token, @Nullable String oldToken,
                                final ServiceResultListener srl){
        ApiUtils.getApiInstance().putToken(userId,token,oldToken).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                ApiResults sir = new ApiResults();
                sir.setResponseCode(response.code());
                srl.onResult(sir);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ApiResults sir = new ApiResults();
                sir.setException(t);
                srl.onResult(sir);
            }
        });
    }

    /**
     * Endpoint for profile
     */

    public static void updateUser(String userId, String mail, String password, String firstName,
                                  String lastName, String city, String phoneNumber, String address,
                                  final ServiceResultListener srl){
        ApiUtils.getApiInstance().updateUser(userId,mail,password,firstName,lastName,city,phoneNumber,
                address).enqueue(new Callback<UserRetrofit>() {
            @Override
            public void onResponse(Call<UserRetrofit> call, Response<UserRetrofit> response) {
                ApiResults sir = new ApiResults();
                sir.setResponseCode(response.code());
                sir.setUr(response.body());
                srl.onResult(sir);
            }

            @Override
            public void onFailure(Call<UserRetrofit> call, Throwable t) {
                ApiResults sir = new ApiResults();
                sir.setException(t);
                srl.onResult(sir);
            }
        });
    }

    /**
     * Endpoints for followUp
     */

    public static void getLastAppraisal(int id, final ServiceResultListener srl) {
        ApiUtils.getApiInstance().getLastAppraisal(id).enqueue(new Callback<Appraisal>() {
            @Override
            public void onResponse(Call<Appraisal> call, Response<Appraisal> response) {
                ApiResults sir = new ApiResults();
                sir.setResponseCode(response.code());
                sir.setLastAppraisal(response.body());
                srl.onResult(sir);
            }

            @Override
            public void onFailure(Call<Appraisal> call, Throwable t) {
                ApiResults sir = new ApiResults();
                sir.setException(t);
                srl.onResult(sir);
            }
        });
    }

    public static void postAppraisal(String userId,String date, String goal, String sessionsByWeek,
                                        String contraindication, String sportAntecedents,
                                        String helpNeeded, String hasNutritionist,
                                        String comments, final ServiceResultListener srl) {
        ApiUtils.getApiInstance().postAppraisal(userId,date,goal,sessionsByWeek, contraindication,
                sportAntecedents,helpNeeded,hasNutritionist,comments).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                ApiResults sir = new ApiResults();
                sir.setResponseCode(response.code());
                srl.onResult(sir);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ApiResults sir = new ApiResults();
                sir.setException(t);
                srl.onResult(sir);
            }
        });
    }

    public static void getTests(int id, final ServiceResultListener srl) {
        ApiUtils.getApiInstance().getTests(id).enqueue(new Callback<List<Test>>() {
            @Override
            public void onResponse(Call<List<Test>> call, Response<List<Test>> response) {
                ApiResults sir = new ApiResults();
                sir.setResponseCode(response.code());
                sir.setListTest(response.body());
                srl.onResult(sir);
            }

            @Override
            public void onFailure(Call<List<Test>> call, Throwable t) {
                ApiResults sir = new ApiResults();
                sir.setException(t);
                srl.onResult(sir);
            }
        });
    }

    public static void postTest(String userId, String date, String warmUp, String startSpeed,
                                String increase, String frequency, String kneeFlexibility,
                                String shinFlexibility, String hitFootFlexibility, String closedFistGroundFlexibility,
                                String handFlatGroundFlexibility, final ServiceResultListener srl) {
        ApiUtils.getApiInstance().postTest(userId,date,warmUp,startSpeed,increase,frequency,kneeFlexibility,
                shinFlexibility,hitFootFlexibility,closedFistGroundFlexibility,handFlatGroundFlexibility)
                .enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                ApiResults sir = new ApiResults();
                sir.setResponseCode(response.code());
                srl.onResult(sir);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ApiResults sir = new ApiResults();
                sir.setException(t);
                srl.onResult(sir);
            }
        });
    }

    public static void getMeasurements(int id, final ServiceResultListener srl) {
        ApiUtils.getApiInstance().getMeasurements(id).enqueue(new Callback<List<Measurement>>() {
            @Override
            public void onResponse(Call<List<Measurement>> call, Response<List<Measurement>> response) {
                ApiResults sir = new ApiResults();
                sir.setResponseCode(response.code());
                sir.setListMeasurement(response.body());
                srl.onResult(sir);
            }

            @Override
            public void onFailure(Call<List<Measurement>> call, Throwable t) {
                ApiResults sir = new ApiResults();
                sir.setException(t);
                srl.onResult(sir);
            }
        });
    }

    public static void postMeasurement(String userId,String date, String weight,String height, String hipCircumference,String waistCircumference,
                                       String thighCircumference,String armCircumference, final ServiceResultListener srl) {
        ApiUtils.getApiInstance().postMeasurements(userId,date,weight,height,hipCircumference,waistCircumference,
                thighCircumference,armCircumference).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                ApiResults sir = new ApiResults();
                sir.setResponseCode(response.code());
                srl.onResult(sir);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ApiResults sir = new ApiResults();
                sir.setException(t);
                srl.onResult(sir);
            }
        });
    }

    /**
     * Endpoints for prescription
     */

    public static void getPrescription(int id, final ServiceResultListener srl) {
        ApiUtils.getApiInstance().getPrescriptions(id).enqueue(new Callback<List<Prescription>>() {
            @Override
            public void onResponse(Call<List<Prescription>> call, Response<List<Prescription>> response) {
                ApiResults sir = new ApiResults();
                sir.setResponseCode(response.code());
                sir.setListPrescription(response.body());
                srl.onResult(sir);
            }

            @Override
            public void onFailure(Call<List<Prescription>> call, Throwable t) {
                ApiResults sir = new ApiResults();
                sir.setException(t);
                srl.onResult(sir);
            }
        });
    }

    public static void postPrescription(String userId,String date,String exercices, final ServiceResultListener srl) {
        ApiUtils.getApiInstance().postPrescription(userId,date,exercices).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                ApiResults sir = new ApiResults();
                sir.setResponseCode(response.code());
                srl.onResult(sir);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ApiResults sir = new ApiResults();
                sir.setException(t);
                srl.onResult(sir);
            }
        });
    }
}
