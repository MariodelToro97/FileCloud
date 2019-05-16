<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Requisicion extends Model
{
    protected $fillable = ['user','message-text','creator-message'];
}
