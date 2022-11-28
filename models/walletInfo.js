// id, money balance

const mongoose = require("mongoose")

const walletSchema = mongoose.Schema({
    id:{
        type:String,
        require:true
    },
    balance:{
        type:String,
        require:true
    }
},{timestamps:true})

module.exports = mongoose.model("walletdata",walletSchema)