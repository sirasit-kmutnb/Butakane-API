const walletdata = require("../models/walletInfo")
const moneydata = require("../models/moneyInfo")
const debtdata = require("../models/debtInfo")
const borrowdata = require("../models/borrowInfo")
const lenddata = require("../models/lendInfo")
const goaldata = require("../models/goalInfo")
const jwt = require("jsonwebtoken")

exports.getWalletInfo=(req,res)=>{

    const token = req.headers.authorization
    var userinfo = jwt.decode(token)
    if(userinfo){
        var id = userinfo.userID
        walletdata.find({id}).exec((err,data)=>{
            res.json(data)
        })

    }
}

exports.getBorrowInfo=(req,res)=>{

    const token = req.headers.authorization
    var userinfo = jwt.decode(token)
    if(userinfo){
        var id = userinfo.userID
        borrowdata.find({id}).exec((err,data)=>{
            res.json(data)
        })

    }
}

exports.getLendInfo=(req,res)=>{

    const token = req.headers.authorization
    var userinfo = jwt.decode(token)
    if(userinfo){
        var id = userinfo.userID
        lenddata.find({id}).exec((err,data)=>{
            res.json(data)
        })

    }
}

exports.getOverviewData=(req,res)=>{
    const token = req.headers.authorization
    var userinfo = jwt.decode(token)
    if(userinfo){
        var id = userinfo.userID
        walletdata.aggregate([
            {$match:{id}},
            {$lookup:{
                from:"goaldatas",
                localField:"id",
                foreignField:"id",
                as:"goal_data"
            }},
            {$lookup:{
                from:"moneydatas",
                localField:"id",
                foreignField:"id",
                as:"in_out_list"
            }}
        ]).exec((err,data)=>{
            res.json(data[0])
        })
    }
}

exports.getMyDebtdata=(req,res)=>{
    const token = req.headers.authorization
    var userinfo = jwt.decode(token)
    if(userinfo){
        var id = userinfo.userID
        
        lenddata.aggregate(
            [
                {
                $match: {id}
                },
                {
                $lookup: {
                    from: "borrowdatas",
                    localField: "id",
                    foreignField: "id",
                    as: "borrow_balance"
                    }
                },
                {
                    $lookup: {
                        from: "debtdatas",
                        localField: "id",
                        foreignField: "id",
                        as: "debt_list"
                        }
                }
            ]).exec((err,data)=>{
                res.json(data[0])
        })
    }
}

exports.income=(req,res)=>{
    const token = req.headers.authorization
    var userinfo = jwt.decode(token)
    const { amount ,detail } = req.body
    if(userinfo){
        var id = userinfo.userID
        const type = true
        
        switch(true){
            case !amount:{
                return res.status(400).json({error:"Please enter your amount of money"})
                break;
            }
            case isNaN(parseFloat(amount)):{
                return res.status(400).json({error:"This is not a number, please try again."})
                break;
            }
        }

        moneydata.create({id,amount,type,detail},(err,data)=>{
            if(err){
                res.status(400).json({error:err})
                console.log(err);
            }
            res.json(data)
        })

        walletdata.find({id}).exec((err,data)=>{

            if(err) console.log(err)
            console.log(data[0])
            var balanceInt = parseInt(data[0].balance)
            balanceInt = balanceInt + parseInt(amount)
            var balance = String(balanceInt)
            walletdata.findOneAndUpdate({id},{balance}).exec((err,data)=>{
                if(err) console.log(err)
            })
            
        })
    }
}

exports.outcome=(req,res)=>{
    const token = req.headers.authorization
    var userinfo = jwt.decode(token)
    const { amount,detail } = req.body
    if(userinfo){
        var id = userinfo.userID
        const type = false
        
        switch(true){
            case !amount:{
                return res.status(400).json({error:"Please enter your amount of money"})
                break;
            }
            case isNaN(parseFloat(amount)):{
                return res.status(400).json({error:"This is not a number, please try again."})
                break;
            }
        }

        moneydata.create({id,amount,type,detail},(err,data)=>{
            if(err){
                res.status(400).json({error:err})
                console.log(err);
            }
            res.json(data)
        })

        walletdata.find({id}).exec((err,data)=>{

            if(err) console.log(err)
            console.log(data[0])
            var balanceInt = parseInt(data[0].balance)
            balanceInt = balanceInt - parseInt(amount)
            var balance = String(balanceInt)
            walletdata.findOneAndUpdate({id},{balance}).exec((err,data)=>{
                if(err) console.log(err)
            })
            
        })
    }
}

exports.getMoneyData=(req,res)=>{
    const token = req.headers.authorization
    var userinfo = jwt.decode(token)
    if(userinfo){
        var id = userinfo.userID

        moneydata.find({id}).exec((err,data)=>{
            res.json(data)
        })
    }
}

exports.getIncomeSummary = (req,res)=>{
    const token = req.headers.authorization
    var userinfo = jwt.decode(token)
    if(userinfo){
        var id = userinfo.userID

        moneydata.find({id,type:true}).exec((err,data)=>{
            const incomeArr = data.map((data)=>{
                if(((new Date(data.createdAt)).toISOString().slice(0,10))===((new Date()).toISOString().slice(0,10))){
                  return parseInt(data.amount)
                }else{
                  return 0
                }
              
              })
            console.log();
            if(incomeArr.length===0) {
                console.log("heh");
                res.status(200).json(0)
            }
            else{res.status(200).json(incomeArr.reduce((a,b)=>a+b))}
        })
    }
}

exports.getOutcomeSummary = (req,res)=>{
    const token = req.headers.authorization
    var userinfo = jwt.decode(token)
    if(userinfo){
        var id = userinfo.userID

        moneydata.find({id,type:false}).exec((err,data)=>{
            const outcomeArr = data.map((data)=>{
                if(((new Date(data.createdAt)).toISOString().slice(0,10))===((new Date()).toISOString().slice(0,10))){
                  return parseInt(data.amount)
                }else{
                  return 0
                }
              
              })
            if(outcomeArr.length===0){res.status(200).json(0)}
            else{res.status(200).json(outcomeArr.reduce((a,b)=>a+b))}
        })
    }
}

exports.getDebtData=(req,res)=>{
    const token = req.headers.authorization
    var userinfo = jwt.decode(token)
    if(userinfo){
        var id = userinfo.userID
        debtdata.find({id}).exec((err,data)=>{
            res.json(data)
        })
    }
}

exports.getGoalData=(req,res)=>{
    const token = req.headers.authorization
    var userinfo = jwt.decode(token)
    if(userinfo){
        var id = userinfo.userID
        goaldata.find({id}).exec((err,data)=>{
            res.json(data)
        })
    }
}



exports.removeDebt=(req,res)=>{

    const _id = req.body

    debtdata.findByIdAndRemove(_id).exec((err,data)=>{
        if(err) console.log(err);
    })
}

exports.borrow=(req,res)=>{
    const token = req.headers.authorization
    var userinfo = jwt.decode(token)
    const { name,amount,detail } = req.body
    if(userinfo){
        var id = userinfo.userID
        const type = false

        switch(true){
            case !amount:{
                return res.status(400).json({error:"Please enter your amount of money"})
                break;
            }
            case isNaN(parseFloat(amount)):{
                return res.status(400).json({error:"Your amount of money is not a number, please try again."})
                break;
            }
            case !name:{
                return res.status(400).json({error:"Please enter name of loaner"})
                break;
            }
        }

        debtdata.create({id,name,amount,detail,type},(err,data)=>{
            if(err){
                res.status(400).json({error:err})
                console.log(err);
            }
            res.json(data)
        })
        borrowdata.find({id}).exec((err,data)=>{
            if(err) console.log(err)
            var balanceInt = parseInt(data[0].balance)
            balanceInt = balanceInt + parseInt(amount)
            var balance = String(balanceInt)
            borrowdata.findOneAndUpdate({id},{balance}).exec((err,data)=>{
                if(err) console.log(err)
            })
        })        

    }
}

exports.lend=(req,res)=>{
    const token = req.headers.authorization
    var userinfo = jwt.decode(token)
    const { name,amount,detail } = req.body
    if(userinfo){
        var id = userinfo.userID
        const type = true

        switch(true){
            case !amount:{
                return res.status(400).json({error:"Please enter your amount of money"})
                break;
            }
            case isNaN(parseFloat(amount)):{
                return res.status(400).json({error:"Your amount of money is not a number, please try again."})
                break;
            }
            case !name:{
                return res.status(400).json({error:"Please enter name of loaner"})
                break;
            }
        }

        debtdata.create({id,name,amount,detail,type},(err,data)=>{
            if(err){
                res.status(400).json({error:err})
                console.log(err);
            }
            res.json(data)
        })   
        lenddata.find({id}).exec((err,data)=>{
            if(err) console.log(err)
            var balanceInt = parseInt(data[0].balance)
            balanceInt = balanceInt + parseInt(amount)
            var balance = String(balanceInt)
            lenddata.findOneAndUpdate({id},{balance}).exec((err,data)=>{
                if(err) console.log(err)
            })
        })       

    }
}

exports.payBack=(req,res)=>{

    const token = req.headers.authorization
    var userinfo = jwt.decode(token)
    const { _id, amount } = req.body
    if(userinfo){

        var id = userinfo.userID
    
        debtdata.findByIdAndRemove(_id).exec((err,data)=>{
            if(err) console.log(err);
        })

        borrowdata.find({id}).exec((err,data)=>{
            if(err) console.log(err)
            var balanceInt = parseInt(data[0].balance)
            balanceInt = balanceInt - parseInt(amount)
            var balance = String(balanceInt)
            borrowdata.findOneAndUpdate({id},{balance}).exec((err,data)=>{
                if(err) console.log(err)
                res.status(200).json({message:"This debt has been payback successfully."})
            })
        })
    }
}

exports.receiveBack=(req,res)=>{

    const token = req.headers.authorization
    var userinfo = jwt.decode(token)
    const { _id, amount } = req.body
    if(userinfo){

        var id = userinfo.userID
    
        debtdata.findByIdAndRemove(_id).exec((err,data)=>{
            if(err) console.log(err);
        })

        lenddata.find({id}).exec((err,data)=>{
            if(err) console.log(err)
            var balanceInt = parseInt(data[0].balance)
            balanceInt = balanceInt - parseInt(amount)
            var balance = String(balanceInt)
            lenddata.findOneAndUpdate({id},{balance}).exec((err,data)=>{
                if(err) console.log(err)
                res.status(200).json({message:"This debt has been receive back successfully."})
            })
        })
    }
}

exports.myGoal=(req,res)=>{
    const token = req.headers.authorization
    var userinfo = jwt.decode(token)
    const { item, price, url } = req.body
    if(userinfo){

        var id = userinfo.userID
        switch(true){
            case isNaN(parseFloat(price)):{
                return res.status(400).json({error:"Your amount of money is not a number, please try again."})
                break;
            }
        }
        goaldata.findOneAndUpdate({id},{item,price,url}).exec((err,data)=>{
            if(data===null){
                goaldata.create({id,item,price,url},(err,data)=>{
                    if(err){return res.status(400).json({error:err})}
                    else {return res.json("New Goal data has been created.")} 
                })
            }
            else {return res.json("Goal data has been updated.")}
        })

    }
}

exports.addPiggy=(req,res)=>{
    const token = req.headers.authorization
    var userinfo = jwt.decode(token)
    const { amount } = req.body
    if(userinfo){

        var id = userinfo.userID
        switch(true){
            case isNaN(parseFloat(amount)):{
                return res.status(400).json({error:"Your amount of money is not a number, please try again."})
                break;
            }
        }
        goaldata.find({id}).exec((err,data)=>{
            if(err) console.log(err)
            var balanceInt = parseInt(data[0].piggy)
            balanceInt = balanceInt + parseInt(amount)
            var piggy = String(balanceInt)
            goaldata.findOneAndUpdate({id},{piggy}).exec((err,data)=>{
                if(err) console.log(err)
                res.json(`Added ${amount} to your piggy.`)
            })
        })


    }
}

exports.removePiggy=(req,res)=>{
    const token = req.headers.authorization
    var userinfo = jwt.decode(token)
    const { amount } = req.body
    if(userinfo){

        var id = userinfo.userID
        switch(true){
            case isNaN(parseFloat(amount)):{
                return res.status(400).json({error:"Your amount of money is not a number, please try again."})
                break;
            }
        }
        goaldata.find({id}).exec((err,data)=>{
            if(err) console.log(err)
            var balanceInt = parseInt(data[0].piggy)
            balanceInt = balanceInt - parseInt(amount)
            var piggy = String(balanceInt)
            goaldata.findOneAndUpdate({id},{piggy}).exec((err,data)=>{
                if(err) console.log(err)
                res.json(`Removed ${amount} from your piggy.`)
            })
        })
        

    }
}

exports.reachedGoal=(req,res)=>{
    const token = req.headers.authorization
    var userinfo = jwt.decode(token)
    if(userinfo){

        var id = userinfo.userID

        goaldata.find({id}).exec((err,data)=>{
            if(err) console.log(err)
            // if(data[0].piggy===data[0].price){
            //     goaldata.findOneAndUpdate({id},{item:"",price:"",piggy:"",url:""}).exec((err,data)=>{
            //         if(err) console.log(err)
            //     })
            // }
            // if(parseInt(data[0].piggy)>=parseInt(data[0].price)){


            //     goaldata.findOneAndUpdate({id},{item:"",price:"",piggy:"",url:""}).exec((err,data)=>{
            //         if(err) console.log(err)
            //     })
            // }
            goaldata.findOneAndUpdate({id},{item:"",price:"0",piggy:"0",url:""}).exec((err,data)=>{
                if(err) console.log(err)
                res.json(`${data.item} has been reached.`)
            })
        })
    }
}

// exports.deleteData=(req,res)=>{
//     const token = req.headers.authorization
//     var userinfo = jwt.decode(token)
//     if(userinfo){
//         var id = userinfo.userID
        
//         walletdata.findOneAndDelete({id}).exec(err=>{
//             if(err) res.status(400).json(err)
//             res.status(200)
//         })
//         moneydata.find({id}).exec((err,data)=>{
//             if(err) res.status(400).json(err)
//             for (let index = 0; index < data.length; index++) {
//                 moneydata.findByIdAndDelete(data._id)
//             }
//             res.status(200)
//         })
//         debtdata.find({id}).exec((err,data)=>{
//             if(err) res.status(400).json(err)
//             for (let index = 0; index < data.length; index++) {
//                 debtdata.findByIdAndDelete(data._id)
//             }
//             res.status(200)
//         })
//         goaldata.findOneAndDelete({id}).exec(err=>{
//             if(err) res.status(400).json(err)
//             res.status(200)
//         })
//         lenddata.findOneAndDelete({id}).exec(err=>{
//             if(err) res.status(400).json(err)
//             res.status(200)
//         })
//         borrowdata.findOneAndDelete({id}).exec(err=>{
//             if(err) res.status(400).json(err)
//             res.status(200)
//         })
//     }
// }