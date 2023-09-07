package model.error

data class RepositorioError (val mensaje: String, val traza: Throwable?) : GenericError