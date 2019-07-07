<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

#Get Routes
Route::get('/', function () {
    return view('auth/login');
});

Auth::routes();

Route::get('/home', 'HomeController@index')->name('home');

Route::get('/RegistrarAdmin','FirebaseController@index');

Route::get('/Requisicion','FirebaseController@index');

Route::get('/getDocuments','FirebaseController@index');


#Post Routes

Route::post('/RegistrarAdmin','FirebaseController@InsertAdmin');

Route::post('/Requisicion','FirebaseController@InsertReq');

Route::post('/getDocuments','FirebaseController@getDataDocuments');
