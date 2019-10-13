import requests
from google.cloud import vision
from google.cloud.vision import types
from utils.cloud_storage import update_marked_area_cv

client = vision.ImageAnnotatorClient()

def form_calamity_list():
	#Validate with weather data to find the current natural calamity/disaster and form the approriate list of words for checking the safety score
	return ["flood"]

def is_unsafe(id, request):
	content = requests.get(request['img_url']).content
	image = types.Image(content=content)

	labels = label_detection(image)
	web = web_detection(image)
	landmark = landmark_detection(image)

	update_marked_area_cv(id, form_label_list(labels))

	return unsafe_check(labels)

def landmark_detection(image):
	response = client.landmark_detection(image=image)
	landmarks = response.landmark_annotations
	print('Landmarks:')
	for landmark in landmarks:
		print(landmark.description)
		for location in landmark.locations:
			lat_lng = location.lat_lng
			print('Latitude {}'.format(lat_lng.latitude))
			print('Longitude {}'.format(lat_lng.longitude))


def web_detection(image):
	response = client.web_detection(image=image)
	annotations = response.web_detection

	if annotations.best_guess_labels:
		for label in annotations.best_guess_labels:
			print('\nBest guess label: {}'.format(label.label))

	if annotations.pages_with_matching_images:
		print('\n{} Pages with matching images found:'.format(
		len(annotations.pages_with_matching_images)))

		for page in annotations.pages_with_matching_images:
			print('\n\tPage url   : {}'.format(page.url))

			if page.full_matching_images:
				print('\t{} Full Matches found: '.format(
				len(page.full_matching_images)))

			for image in page.full_matching_images:
				print('\t\tImage url  : {}'.format(image.url))

			if page.partial_matching_images:
				print('\t{} Partial Matches found: '.format(
				len(page.partial_matching_images)))

			for image in page.partial_matching_images:
				print('\t\tImage url  : {}'.format(image.url))

			if annotations.web_entities:
				print('\n{} Web entities found: '.format(
				len(annotations.web_entities)))

	return annotations

def unsafe_check(labels):
	for label in labels:
		print(label)
		if label.description.lower() in form_calamity_list() and label.score>0.5:
			return {"unsafe":True, "score":label.score}
		elif label.description.lower() in form_calamity_list() and label.score<=0.5:
			return {"unsafe":False, "score":label.score}
	return {"unsafe":False, "score":0}	

def label_detection(image):
	label_response = client.label_detection(image=image)
	print (label_response)
	labels = label_response.label_annotations
	return labels

def form_label_list(labels):
	label_list = []
	label_dict = {}
	for label in labels:
		label_dict["label"] = label.description.upper()
		label_dict["score"] = round(label.score * 100)
		label_list.append(label_dict)
		label_dict = {}
	return label_list
