import firebase_admin
from firebase_admin import credentials
from firebase_admin import storage
import datetime
import json

from werkzeug.utils import redirect, secure_filename

cred = credentials.Certificate("hacks_cred.json")

app = firebase_admin.initialize_app(cred, {
    'storageBucket': 'hacks-255317.appspot.com'
})

ALLOWED_EXTENSIONS = set(['txt', 'pdf', 'png', 'jpg', 'jpeg', 'gif'])

def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

def upload_img(request):
	bucket = storage.bucket(app=app)
	imagePath = "floods1.jpg"

	img = json.loads(request.form.get('img'))

	imageBlob = bucket.blob()
	imageBlob.upload_from_filename(img)
	print (bucket)

	if 'img' not in request.files:
		return redirect(request.url)
	file = request.files['file']
	if file.filename == '':
		return {"response":"error"}
	if file and allowed_file(file.filename):
		filename = secure_filename(file.filename)
		blob = bucket.blob("unsafe_areas/"+filename)

	return {'response': "success", 'filename': filename}

"""
print(blob.generate_signed_url(datetime.timedelta(seconds=300), method='GET'))
print (dir(blob))
print (blob.get_from_filename("ashwin/floods1.jpg"))
"""