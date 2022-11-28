const express = require("express")
const router = express.Router()
const {register,login} = require('../controllers/authController')

router.post('/reg',register)
router.post('/login',login)

module.exports=router