;**
;**	Logics for room 30 -- INVENTORY_TEXT
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 12, 1989
;**

;**		Discard the "script\script.030" file as it is unused.
;**	This is all just a sham so Jeff will generate a text file,
;**	with strings ending in 00's.


(script#				30)

(public
	rm30 	0
)

(instance rm30 of Code
	(method (init)

;**	Dummy
		(Print "0")

;**	CreditCard
		(Print "How will you ever use this credit card when everyone on
			the island knows you personally and also knows you have current
			assets of zip-ola?!")

;**	Knife
		(Print "This knife would be perfect for slicing your ginsu
 			if it weren't so dull.\n\n(What the hell is a ginsu, anyway?)")

;**	Wood
		(Print "Your granadilla is hard and black.")

;**	Grass
		(Print "The indigenous island grasses have long, thin blades perfectly
			suited to weaving.")

;**	Soap
		(Print "All your life you wanted one of these!")

;**	Money
		(Print "She never saw you coming!
			Twenty dollars is not much money, but it may be all you need.")

;**	Deed
		(Print "\"Whereas & whereas & whereas...\n
			Therein, hereby, corpus delicti, wherefore...\n
			Grant to the bearer by the tribes of Nontoonyt Island
			640 acres of rare virgin forestland.\"")

;**	Towel
		(Print "Wet people enjoy rubbing these all over their naked bodies!")

;**	Keycard				#10
		(Print "\"Fat City\05\nMember in Good Standing\n\nInsert and wait for click.\"")

;**	Divorce
		(Print "Free at last!")

;**	Orchids
		(Print "These appear to be epiphytic members of the family Orchidaceae.")

;**	Key
		(Print "You hope this key will open more than just her
			elevator!\n\n(Heh, heh, heh.)")

;**	Bottle (of Wine)
		(Print "\"Imported from Fresno County\n\nLimited Edition\n\n1,243,153
			of only 3,231,679 bottles\"")

;**	Panties
		(Print "\"Saturday Night Special\"")

;**	Pantyhose
		(Print "\"Super Stretch\nSuper Support\"")

;**	Bra
		(Print "\"Frederick's of Coarsegold\"")

;**	Dress
		(Print "Didn't you write off this dress as a business expense?")

;**	Marker
		(Print "\"Every girl needs a little magic in her life sometimes.\"")

;**	Coconuts				#20
		(Print "At last, you have a pair of nuts!")

;**	Marijuana
		(Print "Patti!
			Shame on you!
			Don't you know marijuana's illegal?
			What do you plan to do with this stuff?")

;**	SharpKnife
		(Print "Be careful not to cut yourself; this knife is now razor sharp!")

;**	Sculpture
		(Print "You've always been a true admirer of the African primitive
			school of sculpture.")

;**	GrassSkirt
		(Print "Now if you can just find a luau somewhere to show off this new
			little number!")

;**	Ones
		(Print "500 one-dollar bills isn't so bad for your first try
			at exotic dancing!")

;**	Tips
		(Print "\"Feed\n the\nKitty!\"")

;**	Lei
		(Print "Everybody doesn't like something, but nobody doesn't
			like a good lei!")

;**	Rope
		(Print "Aren't you the clever one!
			But now that you have a rope long enough to reach across that canyon,
			whatever will you do with it?")

;**	Empty Bottle
		(Print "Exactly why are you carrying around an empty bottle?")

;**	Water					#30
		(Print "There's nothing like a bottle of nice dry Nontoonyt.")

;**	PattiKey
		(Print "This is the key to your penthouse.")

;**	DressShort				#31
		(Print "It may have been expensive when you bought it,
			but it's depreciating rapidly!")

;**	DressWet					#32
		(Print "You're all ready to enter the wet T-shirt contest!")

;**	DressBikini				#33
		(Print "You hope this is the end of the game as you are running
			out of dress!")

;**	SculptureModest		#34
		(Print "This might pass for a native handicraft.")

	)
)
;**			(Print [invMsg (* 2 view)] [invMsg (+ 1 (* 2 view))]
;**			(Print INVENTORY_TEXT view
;**				#title		name
;**				#icon			view 0 0
;**			)

