<!DOCTYPE html>
<html>
<head>
    <title>Liste des produits</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
</head>

<body>
@include("produits.create")

    <div class="container mt-5">

        <h1 class="mb-4">Liste des produits</h1>
       
        <a class="btn btn-primary" href="#" data-bs-toggle="modal" data-bs-target="#addProduitModal">
    Ajouter un produit
</a>

       
        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>Nom</th>
                    <th>Prix</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                @foreach ($produits as $produit)
              

                    <tr>
                        <td>{{ $produit->nom }}</td>
                        <td>{{ $produit->prix }} </td>
                        <td>
                            <a  data-bs-toggle="modal" data-bs-target="#editProduitModal{{ $produit->id }}" class="btn btn-warning btn-sm"> Modifier</a> 
                            <form action="{{ route('produits.destroy', $produit->id) }}" method="POST" class="d-inline">
                                @csrf
                                @method('DELETE')
                                <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce produit ?')">Supprimer</button>
                            </form>
                        </td>
                    </tr>
                    @include("produits.edit")
                @endforeach
            </tbody>
        </table>
        <div class="d-flex justify-content-center">
            {{ $produits->links() }}
        </div>
    </div>

    <!-- Bootstrap JS (optionnel, pour les fonctionnalités interactives) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
    var modals = document.querySelectorAll(".modal");
    modals.forEach(function (modal) {
        console.log("Modal trouvé :", modal.id);
    });
});
    </script>
    
</body>
</html>