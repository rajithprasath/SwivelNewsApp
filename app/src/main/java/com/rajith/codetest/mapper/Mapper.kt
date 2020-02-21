package com.rajith.codetest.mapper

abstract class Mapper<in I, out O> {
    abstract fun map(input : I) :  O
}