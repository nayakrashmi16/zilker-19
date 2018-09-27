const path = require('path');
const fs = require('fs');
const express = require('express');
const multer = require('multer');
const bodyParser = require('body-parser');
const app = express();
const router = express.Router();

const DIR = './uploads';
var filename;

let storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, DIR);
    },
    filename: (req, file, cb) => {
        filename = file.fieldname + '-' + Date.now() + '.' + path.extname(file.originalname);
        cb(null, filename);
    }
});

let upload = multer({ storage: storage });

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.use(function (req, res, next) {
    res.setHeader('Access-Control-Allow-Origin', 'http://localhost:4200');
    res.setHeader('Access-Control-Allow-Methods', 'POST');
    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');
    res.setHeader('Access-Control-Allow-Credentials', true);
    next();
});

app.get('/', function (req, res) {
    res.end("File Uploader GET");
});

app.post('/upload', upload.single('photo'), function(req, res) {
    if(!req.file) {
        console.log("No file uploaded!");
        return res.send({

            success: false
        });
    } else {
        console.log("File Uploaded!");
        return res.send({
            success: true,
            filename: filename
        })
    }
});

app.post('/messages', function(req, res) {
    return res.send({
        success: true,
        message: "K, whatever"
    })
});

const PORT = process.env.PORT || 4000;

app.listen(PORT, function() {
    console.log("File Upload Server running!");
})