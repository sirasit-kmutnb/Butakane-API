const express = require("express")
const morgan = require("morgan")
const cors = require("cors")
const mongoose = require("mongoose")


require("dotenv").config()

const authRoute = require("./routes/authentication")
const walletRoute = require("./routes/wallet")

const app = express()


//connect to database
mongoose.connect(process.env.DATABASE,{
    useNewUrlParser:true,
    useUnifiedTopology:false
}).then(()=>{
    console.log("Database connected");
}).catch((err)=>{
    console.log(err);
})

//Middleware
app.use(express.json())
app.use(cors())
app.use(morgan("dev"))

//route
app.use('/api',authRoute)
app.use('/api',walletRoute)

const port = process.env.PORT || 8080
app.listen(port,()=>{
    console.log(`Start server in port ${port}`);
})