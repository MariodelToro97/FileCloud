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

Route::get('/', function () {
    return view('auth/login');
});

Auth::routes();

Route::get('/home', 'HomeController@index')->name('home');

Route::get('/historialRequisiciones', 'HistorialRequisicionController@index');

Route::get('/phpfirebase_sdk','FirebaseController@index');

Route::post('/RegistrarAdmin','FirebaseController@InsertAdmin');

Route::post('/Requisicion','FirebaseController@InsertReq');

Route::get('/phpfirebase','FirebaseController@getDataReq');

Route::get('/getDocuments','FirebaseController@getDataDocuments');

