# Assignment
x <- c(10.4, 5.6, 3.1, 6.4, 21.7)
y <- c(x, 0, x)


# Vector arithmetic
v <- 2 * x + 1
sum((x-mean(x))^2)/(length(x)-1)
sqrt(-17+0i)


# Generating sequences
1:30
s3 <- seq(-5, 5, by=.2)
s4 <- seq(length=51, from=-5, by=.2)
s5 <- rep(x, times=5)
s6 <- rep(x, each=5)


# Logical vectors
temp <- x > 13


# Missing values
z <- c(1:3,NA)
ind <- is.na(z)
0/0


# Character vectors
labs <- paste(c("X","Y"), 1:10, sep="")


# Index vectors
y <- x[!is.na(x)]
z <- (x+1)[(!is.na(x)) & x>0]
x[1:10]
y <- x[-(1:5)]

fruit <- c(5, 10, 1, 20)
names(fruit) <- c("orange", "banana", "apple", "peach")
lunch <- fruit[c("apple","orange")]


# Ordered and unordered factors
state <- c("tas", "sa",  "qld", "nsw", "nsw", "nt",  "wa",  "wa",
           "qld", "vic", "nsw", "vic", "qld", "qld", "sa",  "tas",
           "sa",  "nt",  "wa",  "vic", "qld", "nsw", "nsw", "wa",
           "sa",  "act", "nsw", "vic", "vic", "act")
statef <- factor(state)
statef
levels(statef)

incomes <- c(60, 49, 40, 61, 64, 60, 59, 54, 62, 69, 70, 42, 56,
             61, 61, 61, 58, 51, 48, 65, 49, 49, 41, 48, 52, 46,
             59, 46, 58, 43)
incmeans <- tapply(incomes, statef, mean)
incmeans

stderr <- function(x) sqrt(var(x)/length(x))
incster <- tapply(incomes, statef, stderr)
incster


# Arrays
x <- array(1:20, dim=c(4,5))
x

i <- array(c(1:3,3:1), dim=c(3,2))
i

x[i]
x[i] <- 0 
x


# Outer product
xy <- outer(x, y, "*")
f <- function(x, y) cos(y)/(1 + x^2)
z <- outer(x, y, f)


# Matrix operations
x = c(1:4)
A = matrix(1:16, 4, 4)
B = matrix(1:16, 4, 4)

A * B
A %*% B
x %*% A %*% x

ev <- eigen(A)
evals <- eigen(A)$values


# Frequency tables
statefr <- table(statef)

incomef <- factor(cut(incomes, breaks = 35+10*(0:7)))
table(incomef, statef)


# Lists
Lst <- list(name="Fred", wife="Mary", no.children=3,
            child.ages=c(4,7,9))
Lst


# Data frames
accountants <- data.frame(home=statef, loot=incomes, shot=incomef)
accountants