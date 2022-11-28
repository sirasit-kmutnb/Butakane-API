// id, item, price, url

const mongoose = require("mongoose")

const goalSchema = mongoose.Schema({

    id:{
        type:String,
        require:true
    },
    item:{
        type:String,
        require:true
    },
    price:{
        type:String,
        require:true
    },
    piggy:{
        type:String,
        require:true
    },
    url:{
        type:String
    }

},{timestamps:true})

module.exports = mongoose.model("goaldata",goalSchema)