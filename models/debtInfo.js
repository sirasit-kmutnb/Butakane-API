//name ,amount , detail , type

const mongoose = require("mongoose")

const debtSchema = mongoose.Schema({
    id:{
        type:String,
        require:true
    },
    name:{
        type:String,
        require:true
    },
    amount:{
        type:String,
        require:true
    },
    detail:{
        type:String
    },
    type:{
        type:Boolean,
        require:true
    }
},{timestamps:true})


module.exports = mongoose.model("debtdatas",debtSchema)