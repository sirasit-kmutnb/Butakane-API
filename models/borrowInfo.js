// id, money balance

const mongoose = require("mongoose")

const borrowSchema = mongoose.Schema({
    id:{
        type:String,
        require:true
    },
    balance:{
        type:String,
        require:true
    }
},{timestamps:true})

module.exports = mongoose.model("borrowdata",borrowSchema)