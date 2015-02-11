# -*- coding: utf-8 -*-

#####
# YourName .~= MODIFY THIS =~.
###

import pdb #Use pdb.set_trace() to make a break in your code.
import numpy as np

###################
# Solve sudoku #
###################

#####
# reduce: Fonction used by AC3 to reduce the domain of Xi using constraints from Xj.
#
# Xi: Variable (tuple (Y,X)) with reduced domain, if possible.
#
# Xj: Variable (tuple (Y,X)) with reduced domain, if possible.
#
# csp: Object of class CSP containing all information relative to constraint 
#      satisfaction for Sudoku.
#
# return: A tuple containing a boolean indicating if there were changes to the domain, and the csp.
### 

def reduce(Xi, Xj, csp):
    return False,csp



#####
# AC3: Function used to reduce the domain of variables using AC3 algorithm.
#
# csp: Object of class CSP containing all information relative to constraint 
#      satisfaction for Sudoku.
#
# return: A tuple containing the optimized csp and a boolean variable indicating if there are no violated constraints.
### 

def AC3(csp):
    return csp,True



#####
# is_compatible: Fonction verifying the correctness of an assignment.
#
# X: Tuple containing the position in y and in x of the cell concerned by the assignment.
#
# v: String representing the value (between [1-9]) affected by the assignment.
#
# assignment: dict mapping cells (tuple (Y,X)) to values.
#
# csp: Object of class CSP containing all information relative to constraint 
#      satisfaction for Sudoku.
#
# return: A boolean indicating if the assignment of the value v in cell X is legal.
### 

def is_compatible(X,v, assignment, csp):
    return True



#####
# backtrack : Function used to find the missing assignments on the Sudoku grid using Backtracking Search.
#
#
# assignment: dict mapping cells (tuple (Y,X)) to values.
#
# csp: Object of class CSP containing all information relative to constraint 
#      satisfaction for Sudoku.
#
# retour: The dictionary of assignments (cell => value)
### 

def backtrack(assignments, csp):
    return assignments



#####
# backtracking_search : Main function for backgracking
#
# csp: Object of class CSP containing all information relative to constraint 
#      satisfaction for Sudoku.
# The member variables are:
#      'variables'   : list of cases (tuple (Y,X)) 
#      'domaines'    : dict mapping a cell to a list of possible values
#      'contraintes' : dict mapping a cell to a list of cells who's value must be different from the first cell
#
# return: The dictionary of assignments (cell => value)
### 

def backtracking_search(csp):
    return backtrack({}, csp)
