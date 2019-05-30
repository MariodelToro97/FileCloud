<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Kreait\Firebase;
use Kreait\Firebase\Factory;
use Kreait\Firebase\ServiceAccount;
Use App\Admin;
use App\Http\Requests\AdminRequest;
Use App\updateUser;
use App\Http\Requests\updateUserRequest;
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
    public function Requisicion()
    {
        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/DatabaseFirebase.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->create();
        $database = $firebase->getDatabase();
        $db=$database->getReference('Users');
        $allUsers = $db->getvalue();

        return view('Requisicion')->with('allUsers',$allUsers);
    }

    public function InsertAdmin(AdminRequest $request){
        $data = request()->all();
        if (!$this->verifyUser($data['firstName'])){
        $Referece = 'Users/'.$data['firstName'];
        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/DatabaseFirebase.json');
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
                'tipoUsuario' => 0
            ]);
            $this->insert($data);
            $mensaje='El usuario se ha registrado correctamente.';
            return redirect('/home')->with('mensaje',$mensaje);
        }else{
                $mensaje='No se ha pudido completar la operación,el usuario que intenta registra ya existe en la base de datos.';
                return view('inicio')->with('mensaje',$mensaje);
        } 
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
                if($data['documento']!="default"){
                $dataReference = 'Requisiciones/'.$data['user'].'/'.$data['documento'];
                $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/DatabaseFirebase.json');
                $firebase = (new Factory)
                ->withServiceAccount($serviceAccount)
                ->create();
                $database = $firebase->getDatabase();
                $database->getReference($dataReference)->set([
                'usuario' => $data['user'],   
                'mensaje' => $data['message-text'],
                'fecha' => date("d").'-'.date("m").'-'.date("Y"),
                'usuarioCreador' => $data['creator-message']
                ]);
                $mensaje='Se ha hecho la requisición correctamente';
                return view('inicio')->with('mensaje',$mensaje); 
                }else{
                    $mensaje='Ha elejido un documento no válido.';
                    return view('inicio')->with('mensaje',$mensaje);
                }
            }else{
                $mensaje='No se ha podido guardar la requisición, hace referencia a un usuario que no es alumno.';
                return view('inicio')->with('mensaje',$mensaje);
            }
        }else{
            $mensaje='No se encontró el usuario que se hace referencia';
            return view('inicio')->with('mensaje',$mensaje);
        }

    }



    public function verifyuserType($user){
        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/DatabaseFirebase.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->create();
        $database = $firebase->getDatabase();
        $db=$database->getReference('Users/'.$user);
        $comprobar = $db->getvalue();
        if($comprobar['tipoUsuario']==1 || $comprobar['tipoUsuario']==2){
            return true;
        }else{
            return false;
        }
    }

    public function verifyUser($user){
        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/DatabaseFirebase.json');
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

    public function getDataDocuments(userSearchRequest $request){
        $data = request()->all();
        if($this->verifyUser($data['userSearch'])){
            if($this->verifyuserType($data['userSearch'])){
                $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/DatabaseFirebase.json');
                $firebase = (new Factory)
                ->withServiceAccount($serviceAccount)
                ->create();
                $database = $firebase->getDatabase();
                $db=$database->getReference('DOCUMENTS/'.$data['userSearch'].'/ACTA');
                $acta = $db->getvalue();
                $db=$database->getReference('DOCUMENTS/'.$data['userSearch'].'/CURP');
                $CURP = $db->getvalue();
                $db=$database->getReference('DOCUMENTS/'.$data['userSearch'].'/CERTIFICADO');
                $certificado = $db->getvalue();
                $db=$database->getReference('DOCUMENTS/'.$data['userSearch'].'/RECIBO');
                $recibo = $db->getvalue();
                $usuario=$data['userSearch'];
                $documentos    = array("url1"=>$acta['urlDocumento'],"fecha1"=>$acta['FechaCarga'],"url2" => $CURP['urlDocumento'],"fecha2"=>$CURP['FechaCarga'],"url3" => $certificado['urlDocumento'],"fecha3"=>$certificado['FechaCarga'],"url4" => $recibo['urlDocumento'],"fecha4"=>$recibo['FechaCarga'], "usuario"=>$usuario);

                return view('inicio')->with('documentos',$documentos);
            }
            else{
                $mensaje='El usuario'.$data['userSearch'].' no se considera alumno, verifique bien el nombre.';
                return view('inicio')->with('mensaje',$mensaje);   
            }
        }else{
            $mensaje='El usuario "'.$data['userSearch'].'" no existe.';
            return view('inicio')->with('mensaje',$mensaje);
        }
    }


    public function updateuser(updateUserRequest $request){
        $data = request()->all();
        $dataReference ='Users/'.$data['usuarioNew'];
        $datos = $this -> bringDataUser($data['usuarioNew']);
        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/DatabaseFirebase.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->create();
        $database = $firebase->getDatabase();
        $database->getReference($dataReference)->set([
            'Usuario' => $datos['Usuario'] ,
            'Password' => $datos['Password'],
            'Nombre' => $datos['Nombre'],
            'apellidoPaterno' => $datos['apellidoPaterno'],
            'apellidoMaterno' => $datos['apellidoMaterno'],
            'Correo' => $datos['Correo'],
            'cuentaVerificada' => 1,
            'Telefono' => $datos['Telefono'],
            'fechaRegistro' => $datos['fechaRegistro'],
            'tipoUsuario' => $datos['tipoUsuario']
        ]);

        $dataReference ='newUsers/'.$data['usuarioNew'];
        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/DatabaseFirebase.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->create();
        $database = $firebase->getDatabase();
        $database->getReference($dataReference)->remove();

        /* $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/DatabaseFirebase.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->create();
        $database = $firebase->getDatabase();
        $database->getStorageRef('nuevosUsuarios/'.$data['usuarioNew'].'.pdf')->remove(); */

        return redirect ('/usersapprove');
    }

    public function bringDataUser($user){
        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/DatabaseFirebase.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->create();
        $database = $firebase->getDatabase();
        $db=$database->getReference('Users/'.$user);
        $lista = $db->getvalue();

        /* print_r($lista); */
        return $lista;

    }

    public function deleteUser(updateUserRequest $request){
        $data = request()->all();
        $dataReference ='newUsers/'.$data['usuarioNew'];
        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/DatabaseFirebase.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->create();
        $database = $firebase->getDatabase();
        $database->getReference($dataReference)->remove();

        $dataReference ='Users/'.$data['usuarioNew'];
        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/DatabaseFirebase.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->create();
        $database = $firebase->getDatabase();
        $database->getReference($dataReference)->remove();

/*         $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/DatabaseFirebase.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->create();
        $database = $firebase->getDatabase();
        $database->storage.ref('nuevosUsuarios/'.$data['usuarioNew'].'.pdf')->remove(); */

        return redirect ('/usersapprove');
    }
 


    public function bringnewusers(){
        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/DatabaseFirebase.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->create();
        $database = $firebase->getDatabase();
        $db=$database->getReference('newUsers');
        $users = $db->getvalue();
        return view ('aproveNewUsers') -> with('users',$users);

    }

    public function list(){
        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/DatabaseFirebase.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->create();
        $database = $firebase->getDatabase();
        $db=$database->getReference('Users');
        $users = $db->getvalue();
        return view ('userList')->with('users',$users);
    }
}