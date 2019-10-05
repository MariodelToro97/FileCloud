<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Admin extends Model
{
    protected $fillable = ['firstName','lastName','secondLastName','password','email','address'];
}
