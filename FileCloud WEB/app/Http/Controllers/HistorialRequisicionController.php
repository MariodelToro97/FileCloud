<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Kreait\Firebase;
use Kreait\Firebase\Factory;
use Kreait\Firebase\ServiceAccount;
Use App\Historial;
use App\Http\Requests\historialRequest;

class HistorialRequisicionController extends Controller
{
    public function index(){
        return redirect('/home');
    }

    public function historial(historialRequest $request){
        $data = request()->all();
        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/pruebafilecloud-firebase-adminsdk-1eylc-10240bee9b.json');
        $firebase = (new Factory)
        ->withServiceAccount($serviceAccount)
        ->create();
        $database = $firebase->getDatabase();
        $db=$database->getReference('HistorialReq/'. $data['userSession'] .'/ACTA');
        $Requsiciones = $db->getvalue();
        $hola=array_merge($Requsiciones);
        dd($hola);
    }
}
