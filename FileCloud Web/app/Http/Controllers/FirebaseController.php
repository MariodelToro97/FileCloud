<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Kreait\Firebase;
use Kreait\Firebase\Factory;
use Kreait\Firebase\ServiceAccount;
Use App\Admin;
use App\Http\Requests\AdminRequest;
Use App\Requisicion;
use App\Http\Requests\RequisicionRequest;


class FirebaseController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        //Esto es una prueba, no se debe utilizar en las funcionalidades del sistema
        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/filecloud-bcc76-firebase-adminsdk-r6ts2-f202f07664.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->withDatabaseUri('https://filecloud-bcc76.firebaseio.com/')
        ->create();
        $database = $firebase->getDatabase();
        $db = $database
        ->getReference('Documentos/Jose Miguel/Acta de Nacimiento')
        ->set([
        'Archivo' => 'Acta.pdf' ,
        'Fecha' => '12/08/2018'
        ]);

        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/filecloud-bcc76-firebase-adminsdk-r6ts2-f202f07664.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->withDatabaseUri('https://filecloud-bcc76.firebaseio.com/')
        ->create();
        $database = $firebase->getDatabase();
        $db = $database
        ->getReference('Documentos/Jose Miguel/CURP')
        ->set([
        'Archivo' => 'CURP.pdf' ,
        'Fecha' => '20/11/2018'
        ]);
        
        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/filecloud-bcc76-firebase-adminsdk-r6ts2-f202f07664.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->withDatabaseUri('https://filecloud-bcc76.firebaseio.com/')
        ->create();
        $database = $firebase->getDatabase();
        $db = $database
        ->getReference('Documentos/Jose Miguel/Comprobante')
        ->set([
        'Archivo' => 'Comprobante.pdf' ,
        'Fecha' => '23/03/2018'
        ]);
    }

    

    public function InsertAdmin(AdminRequest $request){
        $data = request()->all();
        $Referece = 'Admin/'.$data['username'];
        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/filecloud-bcc76-firebase-adminsdk-r6ts2-f202f07664.json');
        $firebase = (new Factory)
            ->withServiceAccount($serviceAccount)
            ->create();
            $database = $firebase->getDatabase();
            $database->getReference($Referece)->set([
                'Usuario' => $data['username'] ,
                'Password' => $data['password'],
                'Nombre' => $data['firstName'],
                'ApellidoPaterno' => $data['lastName'],
                'ApellidoMaterno' => $data['secondLastName'],
                'email' => $data['email'],
                'Telefono' => $data['address']
            ]);
         
        return redirect('/home')->with('<h1>The data has been Inserted</h1>');
    }

    public function InsertReq(RequisicionRequest $request){
        $data = request()->all();
        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/filecloud-bcc76-firebase-adminsdk-r6ts2-f202f07664.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->withDatabaseUri('https://filecloud-bcc76.firebaseio.com/')
        ->create();
        $database = $firebase->getDatabase();
        $database->getReference('Requisiciones/'.$data['user'])->set([
        'Usuario' => $data['user'],   
        'Mensaje' => $data['message-text'],
        'UsuarioCreador' => $data['creator-message']
        ]);

        return redirect('/home')->with('<h1>The data has been Inserted</h1>');

    }

    public function getDataReq(){
        /* $data = request()->all(); */
        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/filecloud-bcc76-firebase-adminsdk-r6ts2-f202f07664.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->withDatabaseUri('https://filecloud-bcc76.firebaseio.com/')
        ->create();
        $database = $firebase->getDatabase();
        $db=$database->getReference('Requisiciones/user');
        print_r($db->getvalue());
        $requisicion = $db->getvalue();
        return view('/historialRequisiciones')->with('Requisicion',$requisicion);
    }

    public function getDataDocuments(){
        $data = request()->all();
        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/filecloud-bcc76-firebase-adminsdk-r6ts2-f202f07664.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->withDatabaseUri('https://filecloud-bcc76.firebaseio.com/')
        ->create();
        $database = $firebase->getDatabase();
        $db=$database->getReference('Documentos/Jose Miguel/Acta de Nacimiento');
        $acta = $db->getvalue();
        $db=$database->getReference('Documentos/Jose Miguel/CURP');
        $CURP = $db->getvalue();
        $db=$database->getReference('Documentos/Jose Miguel/Comprobante');
        $Comprobante = $db->getvalue();
/*         print_r($acta);
        print_r($CURP);
        print_r($Comprobante); */
/*         $documentos = array_merge ($acta, $CURP, $Comprobante);
        print_r($documentos); */
        return view('inicio')->with('acta',$acta,'curp',$CURP,'comprobante',$Comprobante);
    }

    public function userAuthentication(){
        
    }

}