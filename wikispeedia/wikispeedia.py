import urllib
import numpy as np
import math
import re
import time
import heapq

class PriorityQueue:
	def __init__(self):
		self.elements = []

	def is_empty(self):
		return len(self.elements) == 0

	def push(self, item, priority):
		heapq.heappush(self.elements, (priority, item))

	def pop(self):
		return heapq.heappop(self.elements)[1]


#Input is a URL
#Output is a list of the Wikipedia hyperlinks in that webpage
def url_search(input_url):
	temp= urllib.urlopen(input_url).read()
	temp=re.split('<|\"',temp)
	url_list=[]
	for i in range(len(temp)):
		if temp[i]=='a href=':
			link=temp[i+1]
			linksplit=link.split("/")
			if len(linksplit)>1:
				if linksplit[1]=="wiki":
					pagecheck=re.split(':',linksplit[2])
					if len(pagecheck)==1:
						next_url = "http://en.wikipedia.org"+link
						if next_url not in url_list:
							url_list.append(next_url)
	return url_list

#Input is a URL
#Output is a list of unique words on that webpage (contains some non-words)
def word_search(url):
	temp= urllib.urlopen(url).read()
	temp=re.split(' ',temp)
	word_list=[]
	for i in range(len(temp)):
		wordsplit=re.split('<|\"|>|&|=|[|]|\|/',temp[i])
		if len(wordsplit)==1 and temp[i] not in word_list:
			word_list.append(temp[i])

	return word_list


#Iterates over the depth first search algorithm
#Input is the starting and ending URLs, as well as the stop time
#Output is the shortest path to the ending URL
def id_dfs(start, end, stop_time):
	
	depth=0			
	starttime=time.clock()
	time1=starttime

	while (time1-starttime)<stop_time:
		depth=depth+1
		print "At depth %d, we have:" %depth
		path = dfs([start], depth, end)
		if path != None:
			if path[-1]==end:
				return path
		time1=time.clock()


#Actual depth first search algorithm
#Input is the ending URL, maximum depth, and current path
#Output is the final path to the desired depth
def dfs(path, depth, end):
	if depth == 0:
		return
	print "Searching %s" %path[-1]	
	urllist=url_search(path[-1])
	for url in urllist:
		if url==end:
			path = path + [url]
			return path
		if url not in path:
			next_path = dfs(path + [url], depth - 1, end)
			if next_path != None:
				if next_path[-1]==end:
					return next_path


def astar(start, end):
	pq = PriorityQueue()
	pq.push(start, 1)

	end_split = end.split("/")
        target_word = end_split[-1]
	target_word = target_word.replace("_", " ")

	path = [start]
	h0 = []
	h1 = []

	while not pq.is_empty():
		curr = pq.pop()
				
		if(curr == end):
			if curr not in path:
				path = path + [curr]
			break
		else:
			if curr not in path:
				path = path + [curr]
	
			urllist = url_search(curr)
			print "Searching " + curr
			for url in urllist:
				h = find_heuristic(url, target_word)
				if url == end:
					if url not in path:
						path = path + [url]
						return path
				if h == 0:
					if url not in h0:
						pq.push(url, 0)
						h0.append(url)
				else:
					if url not in h1:
						pq.push(url, 1)
						h1.append(url)

	return path


def find_heuristic(url, target_word):
   	words_list = word_search(url)
	if target_word in words_list:
		return 0
	else:
		return 1


def improved_find_heuristic(url, target_word, target_url):
	words_list = word_search(url)
	urllist = url_search(url)

	heuristic = 0

	if word not in words_list:
		heuristic = heuristic + 1

	if target_url not in urllist:
		heuristic = heuristic + 1

	return heuristic

starting_url = "http://en.wikipedia.org/wiki/Guitar" #Insert starting URL
ending_url = "http://en.wikipedia.org/wiki/Frankenstein" #Insert the ending URL

stop_time=300 #Number of seconds before program stops

#idpath=id_dfs(starting_url,ending_url, stop_time)

res = astar(starting_url,ending_url)

print res

#print idpath

#Or print "No solution found in the given time frame"  if this is true.
