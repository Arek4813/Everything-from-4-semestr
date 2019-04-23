package main

import (
	"fmt"
	"math/rand"
	"os"
	"time"
)

//struct of one task
type task struct {
	firstArg int
	secondArg int
	operationSign string
}

//defining constants - max amount of arrays, sleep time for go routines, amount of go routines
var listOfTasksMaxAmount = 10
var listOfTasks []task

var productContainerMaxAmount = 10
var productContainer []int

var x1, x2, tmp int
var exitChannel = make(chan bool)
var argsWithProg, argsWithoutProg[]string

var chefTimeRangeOfRand = 2000
var workerTimerSec = 3
var clientTimerSec = 5
var workerAmount = 3
var clientAmount = 3

var listOfSigns = [3]string{"+", "-", "*"}





//BOSS MODE


// -> ADDING ONE TASK IN RANDOMLY TIME INTERVALS, ENDLESS LOOP
func taskAdder(t task, ch chan task) {

	var sign string

	for {

		s1 := rand.NewSource(time.Now().UnixNano())
		r1 := rand.New(s1)
		x1 = r1.Intn(1000) + 1
		x2 = r1.Intn(1000) + 1
		tmp = r1.Intn(len(listOfSigns))
		sign = listOfSigns[tmp]

		t = task{x1, x2, sign}

		if len(listOfTasks)<=listOfTasksMaxAmount {

			listOfTasks = append(listOfTasks, t)

			ch <- t

			fmt.Printf("I have planned a task with %s sign.\n", sign)
			fmt.Println("Current list of tasks: ", listOfTasks)
		} else {
			fmt.Println("I have reached the maximum possible amount of tasks")
		}

		taskTimeDecider := r1.Intn(chefTimeRangeOfRand) + 1
		time.Sleep(time.Duration(taskTimeDecider) * time.Millisecond)
	}
	exitChannel <-true
}


// -> ADDING ONE TASK IN RANDOMLY TIME INTERVALS, ENDLESS LOOP, WITHOUT COMMENTS -- SPECIALIZED FOR SILENT MODE
func taskAdderS(t task, ch chan task) {

	var sign string

	for {

		s1 := rand.NewSource(time.Now().UnixNano())
		r1 := rand.New(s1)
		x1 = r1.Intn(1000) + 1
		x2 = r1.Intn(1000) + 1
		tmp = r1.Intn(len(listOfSigns))
		sign = listOfSigns[tmp]

		t = task{x1, x2, sign}
		if len(listOfTasks)<=listOfTasksMaxAmount {
			listOfTasks = append(listOfTasks, t)

			ch <- t
		}

		taskTimeDecider := r1.Intn(1500) + 1
		time.Sleep(time.Duration(taskTimeDecider) * time.Millisecond)
	}
	exitChannel <-true
}




// -> ADDING ONE TASK, WITHOUT ENDLESS LOOP, FOR MANUAL SETTINGS
func taskAdderOnlyOne(t task, ch chan task) {

	var sign string

	s1 := rand.NewSource(time.Now().UnixNano())
	r1 := rand.New(s1)
	x1 = r1.Intn(1000) + 1
	x2 = r1.Intn(1000) + 1
	tmp = r1.Intn(len(listOfSigns))
	sign = listOfSigns[tmp]

	t = task{x1, x2, sign}
	if len(listOfTasks)<=listOfTasksMaxAmount {

		listOfTasks = append(listOfTasks, t)

		ch <- t

		fmt.Printf("I have planned a task with %s sign.\n", sign)
		fmt.Println("Current list of tasks: ", listOfTasks)
	} else {
		fmt.Println("I have reached the maximum possible amount of tasks")
	}

	taskTimeDecider := r1.Intn(chefTimeRangeOfRand) + 1
	time.Sleep(time.Duration(taskTimeDecider) * time.Millisecond)

	exitChannel <-true
}







//WORKER MODE


// -> ADDING ONE PRODUCT IN CONSTANT(PRE-DEFINED) TIME INTERVALS, ENDLESS LOOP
func productAdder(chFrom chan task, chTo chan int) {

	var takenTask task
	var x1, x2, result int
	var sign string

	for {
		takenTask = <-chFrom
		//deleting takenTask from taskArray
		ctr := 0
		for ctr < len(listOfTasks) {
			if listOfTasks[ctr] == takenTask {
				break
			}
			ctr++
		}

		if len(listOfTasks)>0 {
			listOfTasks = append(append(listOfTasks[:ctr], listOfTasks[ctr+1:]...))
		}

		x1 = takenTask.firstArg
		x2 = takenTask.secondArg
		sign = takenTask.operationSign

		switch sign {
		case "+":
			result = x1 + x2
		case "-":
			result = x1 - x2
		case "*":
			result = x1 * x2
		}

		if len(productContainer)<=productContainerMaxAmount {
			productContainer = append(productContainer, result)

			chTo <- result

			fmt.Println("I have added a product with result:", result)
			fmt.Println("Current productContainer state: (AFTER MAKING RESULT) ", productContainer)
		} else {
			fmt.Println("I have reached the maximum possible amount of products in container")
		}

		time.Sleep(time.Duration(workerTimerSec)* time.Second)
	}
	exitChannel <-true
}



// -> ADDING ONE PRODUCT IN CONSTANT(PRE-DEFINED) TIME INTERVALS, ENDLESS LOOP, WITHOUT COMMENTS -- SPECIALIZED FOR SILENT MODE
func productAdderS(chFrom chan task, chTo chan int) {

	var takenTask task
	var x1, x2, result int
	var sign string

	for {
		takenTask = <-chFrom
		//deleting takenTask from taskArray
		ctr := 0
		for ctr < len(listOfTasks) {
			if listOfTasks[ctr] == takenTask {
				break
			}
			ctr++
		}

		if len(listOfTasks)>0 {
			listOfTasks = append(append(listOfTasks[:ctr], listOfTasks[ctr+1:]...))
		}

		x1 = takenTask.firstArg
		x2 = takenTask.secondArg
		sign = takenTask.operationSign

		switch sign {
		case "+":
			result = x1 + x2
		case "-":
			result = x1 - x2
		case "*":
			result = x1 * x2
		}

		if len(productContainer)<=productContainerMaxAmount {
			productContainer = append(productContainer, result)

			chTo <- result
		}
		time.Sleep(time.Duration(workerTimerSec)* time.Second)
	}
	exitChannel <-true
}


// -> ADDING ONE PRODUCT, WITHOUT ENDLESS LOOP, FOR MANUAL SETTINGS
func productAdderOnlyOne(chFrom chan task, chTo chan int) {

	var takenTask task
	var x1, x2, result int
	var sign string

	takenTask = <-chFrom
	//deleting takenTask from taskArray
	ctr := 0
	for ctr < len(listOfTasks) {
		if listOfTasks[ctr] == takenTask {
			break
		}
		ctr++
	}

	if len(listOfTasks)>0 {
		listOfTasks = append(append(listOfTasks[:ctr], listOfTasks[ctr+1:]...))
	}

	x1 = takenTask.firstArg
	x2 = takenTask.secondArg
	sign = takenTask.operationSign

	switch sign {
	case "+":
		result = x1 + x2
	case "-":
		result = x1 - x2
	case "*":
		result = x1 * x2
	}

	if len(productContainer)<=productContainerMaxAmount {
		productContainer = append(productContainer, result)

		chTo <- result

		fmt.Println("I have added a product with result:", result)
		fmt.Println("Current productContainer state: (AFTER MAKING RESULT) ", productContainer)
	} else {
		fmt.Println("I have reached the maximum possible amount of products in container")
	}
	time.Sleep(time.Duration(workerTimerSec)* time.Second)
	exitChannel <-true
}







//CLIENT MODE


// -> BUYING ONE PRODUCT IN CONSTANT(PRE-DEFINED) TIME INTERVALS, ENDLESS LOOP
func clientGrabber(ch chan int) {

	var takenProduct int

	for {
		takenProduct = <-ch
		//deleting takenProduct from productContainer
		ctr := 0
		for ctr < len(productContainer) {
			if productContainer[ctr] == takenProduct {
				break
			}
			ctr++
		}

		if len(productContainer)>0 {
			productContainer = append(append(productContainer[:ctr], productContainer[ctr+1:]...))
		}

		fmt.Println("I have bought a product:", takenProduct)
		fmt.Println("Current productContainer state: (AFTER BUY) ", productContainer)
		time.Sleep(time.Duration(clientTimerSec)*time.Second)
	}
	exitChannel <-true
}


// -> BUYING ONE PRODUCT IN CONSTANT(PRE-DEFINED) TIME INTERVALS, ENDLESS LOOP, WITHOUT COMMENTS -- SPECIALIZED FOR SILENT MODE
func clientGrabberS(ch chan int) {

	var takenProduct int

	for {
		takenProduct = <-ch
		//deleting takenProduct from productContainer
		ctr := 0
		for ctr < len(productContainer) {
			if productContainer[ctr] == takenProduct {
				break
			}
			ctr++
		}

		if len(productContainer)>0 {
			productContainer = append(append(productContainer[:ctr], productContainer[ctr+1:]...))
		}

		time.Sleep(time.Duration(clientTimerSec)*time.Second)
	}
	exitChannel <-true
}


// -> BUYING ONE PRODUCT, WITHOUT ENDLESS LOOP, FOR MANUAL SETTINGS
func clientGrabberOnlyOne(ch chan int) {

	var takenProduct int

	takenProduct = <-ch
	//deleting takenProduct from productContainer
	ctr := 0
	for ctr < len(productContainer) {
		if productContainer[ctr] == takenProduct {
			break
		}
		ctr++
	}

	if len(productContainer)>0 {
		productContainer = append(append(productContainer[:ctr], productContainer[ctr+1:]...))
	}

	fmt.Println("I have bought a product:", takenProduct)
	fmt.Println("Current productContainer state: (AFTER BUY) ", productContainer)
	time.Sleep(time.Duration(clientTimerSec)*time.Second)

	exitChannel <-true
}



//MAIN FUNCTION
func main() {

	argsWithProg = os.Args
	argsWithoutProg = os.Args[1:]

	//two channels to send tasks and products
	productChannel := make(chan int, 10)
	taskChannel := make(chan task, 10)

	mode := os.Args[1]


	//talker mode - showing info about each go routine activity
	//silent mode - everything works in background, we have a few manual options
	if mode=="talker" {

		fmt.Printf("You choosed talker mode.\n")

		go taskAdder(task{4, 6, "-"}, taskChannel)

		counter := 0
		for counter < workerAmount {
			go productAdder(taskChannel, productChannel)
			counter++
		}

		counter = 0
		for counter < clientAmount {
			go clientGrabber(productChannel)
			counter++
		}

		<-exitChannel

	} else if mode=="silent" {

		go taskAdderS(task{4, 6, "-"}, taskChannel)

		counter := 0
		for counter < workerAmount{
			go productAdderS(taskChannel, productChannel)
			counter++
		}

		counter = 0
		for counter < clientAmount {
			go clientGrabberS(productChannel)
			counter++
		}

		fmt.Printf("You choosed silent mode. Decide what you want to do and give me appropriate number.\n")
		var choosed int

		for {
			fmt.Printf("-----THE WHOLE SIMULATION WORK IN BACKGROUND-----\n")
			fmt.Printf("----------> 1) Show me state of productContainer\n")
			fmt.Printf("----------> 2) Show me the list of tasks\n")
			fmt.Printf("----------> 3) Add the task(AS BOSS)\n")
			fmt.Printf("----------> 4) Add the product(AS WORKER)\n")
			fmt.Printf("----------> 5) Buy product(AS CUSTOMER)\n")
			fmt.Printf("\n")

			fmt.Scanf("%d", &choosed)
			switch choosed {
			case 1:
				{
					fmt.Println("Current productContainer state: ", productContainer)
				}
			case 2:
				{
					fmt.Println("Current list of tasks: ", listOfTasks)
				}
			case 3:
				{
					go taskAdderOnlyOne(task{4, 6, "-"}, taskChannel)
				}
			case 4:
				{
					go productAdderOnlyOne(taskChannel, productChannel)
				}
			case 5:
				{
					go clientGrabberOnlyOne(productChannel)
				}
			default:
				{
					fmt.Println("You clicked wrong number! Choose option from 1 to 5!")
				}
			}
			fmt.Printf("\n")
		}
		<-exitChannel
	} else {
		fmt.Printf("You choosed wrong mode. Try talker or silent mode.\n")
	}

	//closing both channels
	close(productChannel)
	close(taskChannel)
}