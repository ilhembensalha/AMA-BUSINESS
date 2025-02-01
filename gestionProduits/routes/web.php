<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\ProduitController;

Route::get('/', function () {
    return view('produits.index');
});


Route::resource('produits', ProduitController::class);
Route::get('/', [ProduitController::class, 'index'])->name('produits.index');
Route::post('/create', [ProduitController::class, 'store'])->name('produits.store');
Route::put('/produits/{id}', [ProduitController::class, 'update'])->name('produits.update');
