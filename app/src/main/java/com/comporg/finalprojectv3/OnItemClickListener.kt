package com.comporg.finalprojectv3

interface OnItemClickListener<T> {
    fun onItemClick(item: T)
    fun onCancelClick(item: T)

}