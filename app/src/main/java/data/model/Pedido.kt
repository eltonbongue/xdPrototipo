package data.model

data class Pedido (

    val pedido_id: Int,
    val mesa_id: Int,
    val produto_id: Int,
    val quantidade: Int,
    val observacoes: String,
    val timestamp: String
    )