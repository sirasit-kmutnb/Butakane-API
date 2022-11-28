// id, money ,type(income,Outcome)

const mongoose = require("mongoose")

const inAndOutSchema = mongoose.Schema({
    id:{
        type:String,
        require:true
    },
    amount:{
        type:String,
        require:true
    },
    type:{
        type:Boolean,
        require:true
    },
    detail:{
        type:String
    }


},{timestamps:true})

module.exports = mongoose.model("moneydata",inAndOutSchema)