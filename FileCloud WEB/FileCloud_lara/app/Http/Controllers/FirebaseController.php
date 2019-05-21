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
Use App\userSearch;
use App\Http\Requests\userSearchRequest;
use App\User;
use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;
use Illuminate\Foundation\Auth\RegistersUsers;


class FirebaseController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */

    public function index(){
        return redirect('/home');
    }
    public function prueba()
    {
        //Esto es una prueba, no se debe utilizar en las funcionalidades del sistema
 /*        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/pruebafilecloud-firebase-adminsdk-1eylc-10240bee9b.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->create();
        $database = $firebase->getDatabase();
        $db = $database
        ->getReference('Documentos/Chomy/ACTA')
        ->set([
        'archivo1' => 'acta.pdf' ,
        'fecha1' => '12/08/2018'
        ]);

        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/pruebafilecloud-firebase-adminsdk-1eylc-10240bee9b.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->create();
        $database = $firebase->getDatabase();
        $db = $database
        ->getReference('Documentos/Chomy/CURP')
        ->set([
        'archivo2' => 'curp.pdf' ,
        'fecha2' => '20/11/2018'
        ]);
        
        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/pruebafilecloud-firebase-adminsdk-1eylc-10240bee9b.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->create();
        $database = $firebase->getDatabase();
        $db = $database
        ->getReference('Documentos/Chomy/CERTIFICADO')
        ->set([
        'archivo3' => 'certificado.pdf' ,
        'fecha3' => '23/03/2018'
        ]);

        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/pruebafilecloud-firebase-adminsdk-1eylc-10240bee9b.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->create();
        $database = $firebase->getDatabase();
        $db = $database
        ->getReference('Documentos/Chomy/RECIBO')
        ->set([
        'archivo4' => 'recibo.pdf' ,
        'fecha4' => '23/03/2018'
        ]); */
    }

    public function InsertAdmin(AdminRequest $request){
        $data = request()->all();
        if (!$this->verifyUser($data['firstName'])){
        $Referece = 'Users/'.$data['firstName'];
        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/pruebafilecloud-firebase-adminsdk-1eylc-10240bee9b.json');
        $firebase = (new Factory)
            ->withServiceAccount($serviceAccount)
            ->create();
            $database = $firebase->getDatabase();
            $database->getReference($Referece)->set([
                'Usuario' => $data['firstName'] ,
                'Password' => Hash::make($data['password']),
                'Nombre' => $data['firstName'].' '.$data['lastName'].' '.$data['secondLastName'],
                'apellidoPaterno' => $data['lastName'],
                'apellidoMaterno' => $data['secondLastName'],
                'Correo' => $data['email'],
                'Telefono' => $data['address'],
                'fechaRegistro' => date("d").'-'.date("m").'-'.date("Y"),
                'TipoUsuario' => '0'
            ]);
            $this->insert($data);
            $mensaje='El usuario se ha registrado correctamente.';
            return view('inicio')->with('mensaje',$mensaje);
        }else{
                $mensaje='No se ha pudido completar la operación,el usuario que intenta registra ya existe en la base de datos.';
                return view('inicio')->with('mensaje',$mensaje);
        } 
        /* return redirect('/home'); */
    }

    public function insert(array $data){
        $nombre = $data['firstName'].' '.$data['lastName'].' '.$data['secondLastName'];
          return User::create([
            'name' => $nombre,
            'email' => $data['email'],
            'password' => Hash::make($data['password']),
        ]);
    }

    public function InsertReq(RequisicionRequest $request){
        $data = request()->all();
        if ($this->verifyUser($data['user'])){
            if($this->verifyuserType($data['user'])){
                $dataReference = 'Requisiciones/'.$data['user'].'/'.$data['documento'];
                $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/pruebafilecloud-firebase-adminsdk-1eylc-10240bee9b.json');
                $firebase = (new Factory)
                ->withServiceAccount($serviceAccount)
                ->create();
                $database = $firebase->getDatabase();
                $database->getReference($dataReference)->set([
                'Usuario' => $data['user'],   
                'Mensaje' => $data['message-text'],
                'Fecha' => date("d").'-'.date("m").'-'.date("Y"),
                'UsuarioCreador' => $data['creator-message']
                ]);

                $dataReference = 'HistorialReq/'.$data['creator-message'].'/'.$data['documento'];
                $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/pruebafilecloud-firebase-adminsdk-1eylc-10240bee9b.json');
                $firebase = (new Factory)
                ->withServiceAccount($serviceAccount)
                ->create();
                $database = $firebase->getDatabase();
                $database->getReference($dataReference)->set([
                'Usuario' => $data['user'],   
                'Mensaje' => $data['message-text'],
                'Fecha' => date("d").'-'.date("m").'-'.date("Y"),
                'UsuarioCreador' => $data['creator-message']
                ]);
                $mensaje='Se ha hecho la requisición correctamente';
                return view('inicio')->with('mensaje',$mensaje); 
            }else{
                $mensaje='No se ha podido guardar la requisición, hace referencia a un usuario que no es alumno.';
                return view('inicio')->with('mensaje',$mensaje);
            }
        }else{
            $mensaje='No se encontró el usuario que se hace referencia';
            return view('inicio')->with('mensaje',$mensaje);
        }
        /* return redirect('/home'); */

    }

    public function insertHistory($datos){
        $dataReference = 'HistorialReq/'.$datos['creator-message'].'/'.$datos['documento'];
        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/pruebafilecloud-firebase-adminsdk-1eylc-10240bee9b.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->create();
        $database = $firebase->getDatabase();
        $database->getReference($dataReference)->set([
        'Usuario' => $datos['user'],   
        'Mensaje' => $datos['message-text'],
        'Fecha' => date("d").'-'.date("m").'-'.date("Y"),
        'UsuarioCreador' => $datos['creator-message']
        ]);
        print_r($datos);
    }

    public function verifyuserType($user){
        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/pruebafilecloud-firebase-adminsdk-1eylc-10240bee9b.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->create();
        $database = $firebase->getDatabase();
        $db=$database->getReference('Users/'.$user);
        $comprobar = $db->getvalue();
        if($comprobar['TipoUsuario']=='1'){
            return true;
        }else{
            return false;
        }
    }

    public function verifyUser($user){
        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/pruebafilecloud-firebase-adminsdk-1eylc-10240bee9b.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->create();
        $database = $firebase->getDatabase();
        $db=$database->getReference('Users/'.$user);
        $comprobar = $db->getvalue();
        if($comprobar==null){
            return false;
        }else{
            return true;
        }
    }

    public function getDataReq(){
        /* $data = request()->all(); */
        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/pruebafilecloud-firebase-adminsdk-1eylc-10240bee9b.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->create();
        $database = $firebase->getDatabase();
        $db=$database->getReference('Requisiciones/user');
        print_r($db->getvalue());
        $requisicion = $db->getvalue();
        return view('/historialRequisiciones')->with('Requisicion',$requisicion);
    }

    public function getDataDocuments(userSearchRequest $request){
        $data = request()->all();
        if($this->verifyUser($data['userSearch'])){
            $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/pruebafilecloud-firebase-adminsdk-1eylc-10240bee9b.json');
            $firebase = (new Factory)
            ->withServiceAccount($serviceAccount)
            ->create();
            $database = $firebase->getDatabase();
            $db=$database->getReference('Documentos/'.$data['userSearch'].'/ACTA');
            $acta = $db->getvalue();
            $db=$database->getReference('Documentos/'.$data['userSearch'].'/CURP');
            $CURP = $db->getvalue();
            $db=$database->getReference('Documentos/'.$data['userSearch'].'/CERTIFICADO');
            $certificado = $db->getvalue();
            $db=$database->getReference('Documentos/'.$data['userSearch'].'/RECIBO');
            $recibo = $db->getvalue();
            $usuario=$data['userSearch'];
            $documentos    = array("archivo1" => $acta['archivo1'],"fecha1"=>$acta['fecha1'],"archivo2" => $CURP['archivo2'],"fecha2"=>$CURP['fecha2'],"archivo3" => $certificado['archivo3'],"fecha3"=>$certificado['fecha3'],"archivo4" => $recibo['archivo4'],"fecha4"=>$recibo['fecha4'], "usuario"=>$usuario);
            return view('inicio')->with('documentos',$documentos);
        }else{
            $mensaje='El usuario "'.$data['userSearch'].'" no existe.';
            return view('inicio')->with('mensaje',$mensaje);
        }
        /* return view('inicio')->with('acta',$acta); */
    }


}