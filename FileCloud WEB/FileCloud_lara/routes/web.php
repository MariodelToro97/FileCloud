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

Route::get('/getDocuments','FirebaseController@index');

Route::get('/usersapprove','FirebaseController@bringnewusers');

Route::get('/Requisicion','FirebaseController@Requisicion');

Route::get('/listUser','FirebaseController@list');

#Post Routes

Route::post('/RegistrarAdmin','FirebaseController@InsertAdmin');

Route::post('/Requisicion','FirebaseController@InsertReq');

Route::post('/getDocuments','FirebaseController@getDataDocuments');

Route::post('/updateUser','FirebaseController@updateuser');

Route :: post('/deleteUser','FirebaseController@deleteUser');

Route::post('/ACTA','FirebaseController@reqacta');

Route::post('/CURP','FirebaseController@reqcurp');

Route::post('/CERTIFICADO','FirebaseController@reqcert');

Route::post('/RECIBO','FirebaseController@reqrecibp');