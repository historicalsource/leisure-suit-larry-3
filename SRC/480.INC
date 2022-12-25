;**
;**	Header for scenes using pic.480 -- Patti's Penthouse Apartment graphics
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	September 12, 1989
;**


;**	This scene is contained in 5 seperate scripts:
;**		480:	P on bed, L is ego. L enters, undresses, gets in bed, goto 
;**		481:	closeup of both on bed. goto
;**		482:	pic480. both on bed, L leaves, P rises, walks to balcony, goto 
;**		483:	P on balcony with telescope
;**		484:	pic480. after balcony scene, P is ego, and forever thereafter.
;**		489:	LoveRegion

;**	480:
;**		L can only get here if P gave him her key in the piano bar.
;**	When L enters this room, P asks him if he brought the wine.
;**	If no, keep HandsOff and walk him back into the elevator.  
;**	L should walk to bed. If 'get<in/bed' L sets down wine on table, 
;**	walks into place to undress and climb in.  Love scene is all HandsOff.  
;**	"You" is Larry.

;**	481:
;**		Uses pic.485.  Closeup of L & P on the bed.

;**	482:
;**		We return to longshot of P & L on bed. As P drifts off to 
;**	sleep, she wonders how she'll tell Arnold, her boy friend, that 
;**	she's dropping him for L.  She murmers a soft "Arnold" in her 
;**	sleep. L hears her and his fragile ego assumes she was so 
;**	unimpressed with his lovemaking that she was thinking of 
;**	someone else.  Crushed, his thermometer limp, he rises 
;**	from the bed, dresses and leaves, vowing to lose himself 
;**	in the jungle to live as a hermit, never to see a woman again!
;**	P soon awakens, feels the empty bed beside her, wonders where L 
;**	went, walks to the balcony, when we switch to 483.  
;**	"You" is never used.  All is third person.

;**	483:
;**		Uses pic.490.  Balcony scene where P sees L disappear thru telescope

;**	484:
;**		We return to find P is our ego, under player control.  
;**	For the rest of the game, we play as P.  P must dress before 
;**	she leaves the room.  She heads for the dressing area 
;**	which contains all her clothing.  She must "get" and "wear" 
;**	each item separately (underpants, bra, pantyhose and dress).  
;**	She must take the wine bottle from the table. "You" is Patti.



(instance aWine of View
	(properties
		view				vRoom
		loop				lWine
		cel				c2glasses
		x					162
		y					115
	)
	(method (init)
		(super init)
		(self
			setPri			10,
			setCel			c2glasses,
			ignoreActors,
			stopUpd,
		)
	)
)
(instance aDoor of Prop
	(properties
		view					vRoom
		loop					lDoor
		x						159
		y						65
		cycleSpeed			1
	)
	(method (init)
		(super init)
		(self
			setPri			cyan,
			ignoreActors,
			stopUpd,
		)
	)
)
(instance aPanties of View
	(properties
		view					vRoom
		loop					lClothes
		cel					cPanties
		x						63
		y						131
	)
	(method (init)
		(super init)
		(self
			setPri			11,
			ignoreActors,
			stopUpd,
		)
	)
)
(instance aBra of View
	(properties
		view					vRoom
		loop					lClothes
		cel					cBra
		x						63
		y						131
	)
	(method (init)
		(super init)
		(self
			setPri			11,
			ignoreActors,
			stopUpd,
		)
	)
)
(instance aPantyhose of View
	(properties
		view					vRoom
		loop					lClothes
		cel					cPantyhose
		x						63
		y						141
	)
	(method (init)
		(super init)
		(self
			setPri			11,
			ignoreActors,
			stopUpd,
		)
	)
)
(instance aDress of Prop
	(properties
		view					vRoom
		loop					lDress
		x						39
		y						107
	)
	(method (init)
		(super init)
		(self
			setPri			11,
			setCel			(if (InRoom iDress 484) 0 else LastCel),
			ignoreActors,
			stopUpd,
		)
	)
)
(instance atpTelescope of PicView
	(properties
		view					vRoom
		loop					lTelescope
		x						160
		y						50
		priority				1
		signal				ignrAct
	)
)

