;**
;**	Logics for room 130 -- Opening Cartoon:  Progress in Paradise!
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	December 18, 1989
;**


(script#				130)


(public
	rm130					0
)

(procedure
	BuildHouse
	AddBuildingToPic
	ChangeFloors
)


;**	loop1 uses cel0, has  8 cells, is 16 pixels/floor @ 7 floors
;**	loop2 uses cel1, has 13 cells, is 13 pixels/floor @ 7 floors
;**	loop3 uses cel2, has  7 cells, is  8 pixels/floor @ 7 floors
;**	loop4 uses cel3, has  7 cells, is 11 pixels/floor @ 7 floors
;**	loop5 uses cel4, has  7 cells, is  7 pixels/floor @ 6 floors
;**	loop6 uses cel5, has  8 cells, is  8 pixels/floor @ 7 floors
;**	loop7 just goto end of loop
;**	loop8 just goto end of loop


(define 	vRoom 						130)
	(define	lBuildings			0)
		(define	cBuilding1	0)
		(define	cBuilding2	1)
		(define	cBuilding3	2)
		(define	cBuilding4	3)
		(define	cBuilding5	4)
		(define	cBuilding6	5)
		(define	cBuilding7	6)
		(define	cBuilding8	7)

	(define	lBuilding1			1)
	(define	lBuilding2			2)
	(define	lBuilding3			3)
	(define	lBuilding4			4)
	(define	lBuilding5			5)
	(define	lBuilding6			6)
	(define	lBuilding7			7)
	(define	lBuilding8			8)
	(define	lLittleBoxes		9)



(enum												;**	RoomScript state values
	intro
	sinceLast
	theIsland
	nativesInc
	destroyedIsland
	startBuilding1
	changeFloors1
	endBuilding1
	startBuilding2
	changeFloors2
	endBuilding2
	startBuilding3
	changeFloors3
	endBuilding3
	startBuilding4
	changeFloors4
	endBuilding4
	startBuilding5
	changeFloors5
	endBuilding5
	startBuilding6a
	changeFloors6a
	endBuilding6a
	startBuilding6b
	changeFloors6b
	endBuilding6b
	startBuilding6c
	changeFloors6c
	endBuilding6c
	startBuilding7
	endBuilding7
	startBuilding8a
	endBuilding8a
	startBuilding8b
	endBuilding8b
	welcome
	fadeMusic
	reallyTrulyDone
	demo1
	demo2
)


(local
	pixelsPerFloor
	numberOfFloors
	houseCount
	houseXY = [ 158 73						;**	x/y's of ticky-tacky houses
					172 73
					185 73
					197 73
					205 65
					192 65
					178 65
					166 65
					151 66
					140 59
					150 59
					160 59
					171 59
					184 59
					196 59
					189 51
					180 51
					171 51
					162 51
					153 51
					999 999	]
)


(instance rm130 of Room
	(properties
		picture		130
		horizon		1
	)


	(method (init)
		(HandsOff)
		(SetFlg cantSave)
		(SetFlg noCursor)

		(super init)

		(self
			setScript 		RoomScript
		)

		(theGame setSpeed 6)

		(aBuilding			init)
		(aWorkers 			init)
		(aHouse				init)
		(= houseCount -1)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)
	)


	(method (handleEvent event)
		(cond
			((event claimed)
			)
			((and (== (event type) keyDown)
					(== (event message) `#2))
				(TglSound)
;**					(= i (DoSound SoundOn))
;**					(DoSound SoundOn (not i))	;**	Backwards (and proud of it)
;**					(if i
;**						(SetMenu soundI #p_text {Turn On})
;**					else
;**						(SetMenu soundI #p_text {Turn Off})
;**					)
;**					(event claimed)
			)
			((and modelessDialog
					(== (event message) ENTER)
					(== (event type) keyDown))
				(event claimed TRUE)
				(cls)
			)
;**				(demo
;**					(curRoom newRoom 200)
;**				)
			(else
				(curRoom newRoom 140)
			)
		)
	)


;**			(and (not (super handleEvent: event))
;**						(== (event message) ENTER)
;**						(== (event type) keyDown)
;**						)
;**				(event claimed TRUE)
;**				(Printf "building at %d/%d,
;**							workers at %d/%d"
;**					(aBuilding x) (aBuilding y)
;**					(aWorkers x) (aWorkers y) )


	(method (changeState newState)
		(switch (= state newState)

			(intro
				(delay 3)
;**					(if demo
;**						(TheMenuBar draw)
;**						(StatusLine enable)
;**						(= state (- demo1 1))
;**					)
			)

			(sinceLast
				(Print "Since we last saw our intrepid hero Larry Laffer
					cavorting merrily with his newly-found true love
					on the sands of Nontoonyt Beach,
					this beautiful island has undergone extensive \"improvements.\""
					BIG #time 12)
				(= seconds 3)
			)

			(theIsland
				(Print "Once pristine and primitive, covered by virgin rain
					forest, splashed by gurgling streams and washed by tropical
					rains, Nontoonyt has discovered (and been discovered by)
					modern civilization."
					BIG #time 16)
				(= seconds 3)
			)

			(nativesInc
				(Print "The villagers, eager to shed their Stone Age lifestyle,
					united themselves by forming \"Natives, Inc.\05\"
					to protect their interests and develop their island.
					Realizing they were sitting on a yuppie dream,
					a vein of gold they could mine through tourism,
					they took correspondence courses in business management,
					public administration, sales and marketing."
					BIG #time 22)
				(= seconds 3)
			)

			(destroyedIsland
				(Print "Learning modern construction techniques through self-help
					books and pirated videotapes of \"This Old House,\"
					they began by building a hotel on this very same beach."
					#at -1 120 #width 280 #dispose #time 9)
				(= seconds 7)
			)



			(startBuilding1
				(= pixelsPerFloor 16)
				(= numberOfFloors 6)
				(aBuilding
					setCel			cBuilding1,
					posn				22 117,
				)
				(aWorkers
					setLoop			lBuilding1,
					cel				0,
					posn				22 117,
					setCycle			EndLoop self,
				)
			)
			(changeFloors1
				(ChangeFloors)
				(if (-- numberOfFloors)
					(-- state)
				)
			)
			(endBuilding1
				(AddBuildingToPic)
				(Print "Borrowing heavily from foreign investors,
					they expanded into some very attractive tourist traps..."
					#at -1 120 #width 280 #dispose #time 7)
				(= cycles 15)
			)


			(startBuilding2
				(BuildHouse)
				(= pixelsPerFloor 13)
				(= numberOfFloors 6)
				(aBuilding
					setCel			cBuilding2,
					posn				55 116,
				)
				(aWorkers
					setLoop			lBuilding2,
					cel				0,
					posn				55 116,
					setCycle			EndLoop self,
				)
			)
			(changeFloors2
				(ChangeFloors)
				(if (-- numberOfFloors)
					(-- state)
				)
				(if (== numberOfFloors 3)
					(BuildHouse)
				)
			)
			(endBuilding2
				(BuildHouse)
				(AddBuildingToPic)
				(= cycles 15)
			)


			(startBuilding3
				(BuildHouse)
				(= pixelsPerFloor 8)
				(= numberOfFloors 6)
				(aBuilding
					setCel			cBuilding3,
					posn				73 116,
				)
				(aWorkers
					setLoop			lBuilding3,
					cel				0,
					posn				73 116,
					setCycle			EndLoop self,
				)
			)
			(changeFloors3
				(ChangeFloors)
				(if (-- numberOfFloors)
					(-- state)
				)
				(if (== numberOfFloors 3)
					(BuildHouse)
				)
			)
			(endBuilding3
				(BuildHouse)
				(AddBuildingToPic)
				(= cycles 15)
			)


			(startBuilding4
				(BuildHouse)
				(= pixelsPerFloor 11)
				(= numberOfFloors 6)
				(aBuilding
					setCel			cBuilding4,
					posn				96 114,
				)
				(aWorkers
					setLoop			lBuilding4,
					cel				0,
					posn				96 114,
					setCycle			EndLoop self,
				)
			)
			(changeFloors4
				(ChangeFloors)
				(if (-- numberOfFloors)
					(-- state)
				)
				(if (== numberOfFloors 3)
					(BuildHouse)
				)
			)
			(endBuilding4
				(BuildHouse)
				(AddBuildingToPic)
				(= cycles 15)
			)


			(startBuilding5
				(BuildHouse)
				(= pixelsPerFloor 7)
				(= numberOfFloors 5)
				(aBuilding
					setCel			cBuilding5,
					posn				127 110,
				)
				(aWorkers
					setLoop			lBuilding5,
					cel				0,
					posn				127 110,
					setCycle			EndLoop self,
				)
			)
			(changeFloors5
				(ChangeFloors)
				(if (-- numberOfFloors)
					(-- state)
				)
				(if (== numberOfFloors 3)
					(BuildHouse)
				)
			)
			(endBuilding5
				(BuildHouse)
				(AddBuildingToPic)
				(Print "...but growth really exploded when they discovered the
					Holy Grail of modern marketing: the wonder of time-share!"
					#at -1 120 #width 280 #dispose #time 9)
				(= cycles 15)
			)


			(startBuilding6a
				(BuildHouse)
				(= pixelsPerFloor 8)
				(= numberOfFloors 6)
				(aBuilding
					setCel			cBuilding6,
					posn				162 108,
				)
				(aWorkers
					setLoop			lBuilding6,
					cel				0,
					posn				162 108,
					setCycle			EndLoop self,
				)
			)
			(changeFloors6a
				(ChangeFloors)
				(if (-- numberOfFloors)
					(-- state)
				)
				(if (== numberOfFloors 3)
					(BuildHouse)
				)
			)
			(endBuilding6a
				(BuildHouse)
				(AddBuildingToPic)
				(= cycles 15)
			)


			(startBuilding6b
				(BuildHouse)
				(= pixelsPerFloor 8)
				(= numberOfFloors 6)
				(aBuilding
					setCel			cBuilding6,
					posn				193 108,
				)
				(aWorkers
					setLoop			lBuilding6,
					cel				0,
					posn				193 108,
					setCycle			EndLoop self,
				)
			)
			(changeFloors6b
				(ChangeFloors)
				(if (-- numberOfFloors)
					(-- state)
				)
			)
			(endBuilding6b
				(AddBuildingToPic)
				(= cycles 15)
			)


			(startBuilding6c
				(BuildHouse)
				(= pixelsPerFloor 8)
				(= numberOfFloors 6)
				(aBuilding
					setCel			cBuilding6,
					posn				224 108,
				)
				(aWorkers
					setLoop			lBuilding6,
					cel				0,
					posn				224 108,
					setCycle			EndLoop self,
				)
			)
			(changeFloors6c
				(ChangeFloors)
				(if (-- numberOfFloors)
					(-- state)
				)
			)
			(endBuilding6c
				(BuildHouse)
				(AddBuildingToPic)
				(Print "Ah, civilization! Who would want real palm trees
					when you never have to prune or water those
					new plastic models?"
					#at -1 120 #width 280 #dispose #time 9)
				(= cycles 15)
			)


			(startBuilding7
				(= pixelsPerFloor 0)			;**	For AddBuildingToPic proc.
				(aBuilding
					setLoop			lBuilding7,
					cel				0,
					cycleSpeed		1,
					posn				256 107,
					setCycle			EndLoop self,
				)
			)
			(endBuilding7
				(BuildHouse)
				(AddBuildingToPic)
				(= cycles 15)
			)


			(startBuilding8a
				(aBuilding
					setLoop			lBuilding8,
					cel				0,
					posn				286 107,
					setCycle			EndLoop self,
				)
			)
			(endBuilding8a
				(BuildHouse)
				(AddBuildingToPic)
				(= cycles 15)
			)


			(startBuilding8b
				(aBuilding
					setLoop			lBuilding8,
					cel				0,
					posn				312 113,
					setCycle			EndLoop self,
				)
			)
			(endBuilding8b
				(AddBuildingToPic)
				(= cycles 15)
			)


			(welcome
				(Print "Welcome to the paradise of the Pacific:
					the all-new, all-improved, totally-yuppiefied,
					\"consumer-orientationalized\" Nontoonyt Island!"
					#at -1 120 #width 280 #time 12 #dispose)
				(= cycles 15)
			)

			(fadeMusic
				(music fade)
				(delay 6)
			)

			(reallyTrulyDone
				(cls)
				(curRoom newRoom 120)
			)


			;**	Here's the sales pitch:

;**				(demo1
;**					(Print "The third installment in the `Trilogy of Polyester'
;**						features our hackneyed hero back to his old swinging single
;**						ways. Larry, still on Nontoonyt Island,
;**						fed up with commitment and `true love,'
;**						is again a loose cannon among the beautiful women of paradise."
;**						BIG #time 14)
;**					(music fade)
;**					(= cycles 3)
;**				)
;**	
;**				(demo2
;**					(curRoom newRoom 200)
;**				)
		)
	)
)


(instance aWorkers of Actor
	(properties
		view				vRoom
		illegalBits		0
		x					23
		y					1174
	)
	(method (init)
		(super init)
		(self
			setLoop			lBuilding1,
			setPri			4,
			ignoreActors,
			ignoreHorizon,
		)
	)
)
(instance aBuilding of Actor
	(properties
		view				vRoom
		illegalBits		0
		x					23
		y					999
	)
	(method (init)
		(super init)
		(self
			setLoop			lBuildings,
			setCel			cBuilding1,
			setPri			3,
			ignoreActors,
			ignoreHorizon,
		)
	)
)
(instance aHouse of Prop
	(properties
		view				vRoom
		loop				lLittleBoxes
		x					999
		y					999
	)
)

(procedure (BuildHouse)
	(aHouse
		setPri		1,
		posn			[houseXY (++ houseCount)] [houseXY (++ houseCount)]
	)
	(AddActorToPic aHouse)
	(if (== 999 [houseXY houseCount])	;**	error protection
		(-= houseCount 2)
	)
)

(procedure (AddBuildingToPic)
	(aBuilding
		posn				(aBuilding x) ( + 1000 (aBuilding y))
	)
	(aWorkers
		posn				999 999,
	)
	((View new)
		view				(aBuilding view),
		setLoop			(aBuilding loop),
		setCel			(aBuilding cel),
	  	setPri			3,
		posn				(aBuilding x) (- ( - (aBuilding y) 1000) pixelsPerFloor),
		ignoreActors	TRUE,
		addToPic,
	)
)

(procedure (ChangeFloors)
	(aBuilding
		posn				(aBuilding x) (- (aBuilding y) pixelsPerFloor),
	)
	(aWorkers
		posn				(aWorkers x) (- (aWorkers y) pixelsPerFloor),
		cel				0,
		setCycle			EndLoop RoomScript,
	)
)

