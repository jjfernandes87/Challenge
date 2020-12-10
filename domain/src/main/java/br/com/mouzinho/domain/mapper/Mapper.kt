package br.com.mouzinho.domain.mapper

interface Mapper<I, O> {
    fun transform(input: I) : O
    fun transform(input: List<I>): List<O> = input.map(::transform)
}