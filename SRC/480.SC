;**
;**	Logics for room 480 -- Patti's Penthouse Apartment (ego is Larry)
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	January 22, 1990
;**


(script#				480)

(include	"480.sh")
(include	"480.inc")

(public
	rm480				0
)


(procedure
	PrintPatti		1
	PrintLarry		2
)


(define	LarryPosn			176  92)		;**	These are when on the bed.
(define	PattiPosn			151 101)


(enum												;**	RoomScript state values
	intro
	gladYouBroughtWine
	tellHim
	goodBoy
	startHumping
	walkingToWineTable
	atTable
	returningToBed
	returningToBed2
	besideBed
	climbOnBed
	onTheBed
	howAbout
	fineByMe
	getWine
	hasBottle
	pourGlass1
	pourGlass2
	PreachesForWine
	removeGlassFromTray
	LgetsHis
	bothHaveWine
	lowerGlasses
	weveDrunkEnough
	theyKiss
	unKiss
	ohPatti
	ohLarry
	kiss2
	unKiss2
	ohPatti2
	ohLarry2
	rollOnTop
	gotoBlack
	ohLarry3
	mmmmmm
	ahhh
	youAreTheFirst
	whereDidYou
	andThat
	yesThat
	lafferLover
	changeRooms
	preventPenetration
	comfortable
	didYouBringIt
	backToVator
)


(local
	drinkCounter
	[string 200]
)


(instance rm480 of Room
	(properties
		picture		480
		south			470
	)


	(method (init)
		(music fade)
		(Load SCRIPT REVERSE)
;**			(if debugging
;**				(= egoState NORMAL)
;**				(ego get iBottle)
;**			)

		(if (ego has iBottle)
			(Load PICTURE blackPicture)

			(Load VIEW vLroom)

			(Load SOUND sLontoBed)
			(Load SOUND sFirstKiss)
			(Load SOUND sFadeToBlack1)
		else
			(Load SOUND sInterrupted)			
		)

		(super	 	init)

		(aWine				init)
		(aDoor				init)
		(aPanties  			init)
		(aBra					init)
		(aPantyhose  		init)
		(aDress  			init)

		(addToPics
			add 				atpTelescope,
			doit
		)

		(self
			setScript 		RoomScript,
		)

		(aPatti				init)

		(NormalEgo)
		(HandsOff)
		(ego
			posn				159 188,
			loop				facingNorth,
			observeControl	cYellow,
			init,
		)

		(if (ego has iBottle)
			(RoomScript changeState gladYouBroughtWine)
		else
			(RoomScript changeState preventPenetration)
		)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		(if (>= state climbOnBed)
			(theGame setSpeed 6)
		)
	)


	(method (handleEvent event)
		(if (and	(== (event type) keyDown)
					(== (event claimed) FALSE)
					(== (event message) `#8)
					(>= state besideBed))
			(Print "Skipping ahead...")
			(SetFlg tookShortcut)
			(curRoom newRoom 484)
		)

		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((or	(Said 'make/love')
					(Said 'hump')
					(Said 'go, (climb<in, on), (get<in)/bed')
					(Said 'undress') 
					(Said 'get/naked')
					(Said 'pour, give, put/wine')
					(Said 'wear/nothing'))
				(cond
					((and (not NWofBed)
							(not NEofBed)
							(not SWofBed)
							(not SEofBed))
						(NotClose)
					)
					(else
						(self changeState startHumping)
					)
				)
			)

			((Said 'talk')
				(Print "Talk, talk, talk. Doesn't anybody...")
			)

			((Said 'open/door')
				(Print "The door isn't what you're interested in here, Larry!")
			)

			((Said 'get')
				(Print "You're here to get something, but not that!")
			)

			((Said 'look>')
				(cond
					((Said '/balcony, city, ocean, sunset, window')
						(Print "The view from her window is spectacular!")
					)

					((Said '/bed')
						(Print "It (and she) is ready and waiting for you, Larry.")
					)

					((Said '/patti, woman')
						(Print "Is that all you came up here to do: look?!")
					)

					((Said '/clothes')
						(Print "Patti's clothes are lying on a table by her
							dressing screen.")
					)

					((Said '/piano')
						(Print "Practice makes perfect.")
					)

					((Said '/telescope')
						(Print "From her balcony, 
							Patti has a perfect view into the Fat City locker room
							window.")
					)

					((Said '/light')
						(Print "Lamps hang suspended from the ceiling casting a
							dull glow to the surroundings.")
					)

					((Said '/plant')
						(Print "You didn't come here for botany!")
					)

					((Said '/table')
						(Print "A table at the foot of her bed holds two empty
							wine glasses.
							There is a table near her dressing screen that
							holds her clothes.")
					)

					((Said '[/room]')
						(Print "Passionate Patti's penthouse suite is gorgeous.
							Too bad you couldn't negotiate a contract as sweet
							as hers!")
					)
				)
			)
		)
	)


	(method (changeState newState &tmp destX destY)
		(ShowState self newState 1 green)

		(switch (= state newState)

			(intro
			)

			(gladYouBroughtWine
				(delay 3)
			)

			(tellHim
				(if (TstFlg beenIn480)
					(Print "\"I brought the wine again, Patti,\" you announce.")
				else
					(Print "\"I remembered to bring the wine, Patti,\" 
						you announce.")
				)
				(delay 3)
			)

			(goodBoy
				(if (TstFlg beenIn480)
					(Print "\"Where the hell did you go?\" she asks.")
				else
					(SetFlg beenIn480)
					(Print "\"And I remembered to wear your lei, Larry!\" 
						she smiles.")
				)
				(= egoState NORMAL)
				(NormalEgo)
				(ego
					observeControl	cYellow,
				)
			)



			;**	This is the moment we've all been waiting for...

			(startHumping
				(HandsOff)
				(Ok)
				(theGame changeScore 500)
				(Print "You decide to place the bottle of wine on the silver tray
					resting on the table at the foot of the circular bed, 
					beside the two glasses Patti has thoughtfully provided 
					for the occasion.")
				(self cue)
			)

			(walkingToWineTable
				(cond
					((>= (ego y) wineY)
						(= destX wineX)
						(= destY wineY)
					)
					(SWofBed
						(= destX (ego x))
						(= destY wineY)
						(-- state)
					)
					(SEofBed
						(= destX (ego x))
						(= destY wineY)
						(-- state)
					)
					(NWofBed
						(= destX bedWestX)
						(if ( > (ego x) bedWestX)
							(= destY (ego y))
						else
							(= destY bedCenterY)
						)
						(-- state)
					)
					(NEofBed
						(= destX bedEastX)
						(if ( < (ego x) bedEastX)
							(= destY (ego y))
						else
							(= destY bedCenterY)
						)
						(-- state)
					)
					(else
						(Print "error")
					)
				)
				(ego
					setMotion		MoveTo destX destY self,
				)
			)				

			(atTable
				(PutInRoom iBottle 484)
				((Inventory at iBottle) view ivEmptyBottle)
				(Format ((Inventory at iBottle) name) "Empty Bottle")
				(ego
					loop			facingNorth,
				)
				(aWine
					setCel		cAll,
					stopUpd,
				)
				(= cycles 11)
			)

			(returningToBed
				(ego
					setMotion		MoveTo 194 wineY self,
				)
			)

			(returningToBed2
				(ego
					setMotion		MoveTo 194 132 self,
				)
			)

			(besideBed
				(= oldGameSpeed (theGame setSpeed 6))
				(music
					number			sLontoBed,
					loop				FOREVER,
					play,
				)
				(StatusLine	disable)
				(TheMenuBar	
					hide,
					state 			FALSE,
				)
				(SetFlg noCursor)
				(SetFlg preventAutoSave)
				(Format @string "\"Perhaps I should explain...
					I have a history of not finishing what I begin.
					Just don't be surprised if something happens
					before we finish!\"")
				(= seconds (PrintLarry)) 
				(ego 	
					loop				facingSouth,
				)
				(Display "F8 will bypass this scene."
					#p_at: 		0 180
					#p_color:	red
;					#p_width:	130
					#p_font:		userFont
				)
			)

			(climbOnBed
				(ego
					posn				LarryPosn,
					cycleSpeed		1,
					view				vLroom,
					setPri			ltBlack,
					loop				lGettingOnBed,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(onTheBed
				(Format @string "\"Rest assured, Honey; I promise you: 
					something will happen!\"")
				(= seconds (PrintPatti))
			)

			(howAbout
				(Format @string "\"Would you care for a glass of wine?\"")
				(= seconds (PrintLarry)) 
			)

			(fineByMe
				(Format @string "\"I thought you'd never ask.\"")
				(= seconds (PrintPatti))
			)

			(getWine
				(ego
					loop				lGettingBottle,
					cel				0 ,
					setCycle			CycleTo cEraseBottle ctForward self,
				)
			)

			(hasBottle
				(aWine
					setCel			c2glasses,
					stopUpd,
				)
				(ego
					setCycle			EndLoop self,
				)
			)

			(pourGlass1
				(Format @string "\"Say when.\"")
				(= seconds (PrintLarry)) 
				(ego
					loop				lPourGlass1,
					cel				0,
					setCycle			Forward,
				)
			)

			(pourGlass2
				(Format @string "\"Right after this drink, Larry!\"")
				(= seconds (PrintPatti))
				(ego
					loop				lPourGlass2,
					cel				0 ,
					setCycle			Forward,
				)
			)

			(PreachesForWine
				(aPatti
					setCycle			EndLoop ,
				)
				(ego
					loop				lGiveWineToPatti,
					cel				0,
					setCycle			CycleTo cDrawBottle ctForward self,
				)
			)

			(removeGlassFromTray
				(aWine
					setCel			c1glass,
					stopUpd,
				)
				(ego
					setCycle			EndLoop self,
				)
			)

			(LgetsHis
				(aPatti
					loop				lPdrinkingWine,
					cel				0,
				)
				(aWine
					setCel			c0glasses,
					stopUpd,
				)
				(ego
					loop				lGettingLwine,
					cel				0 ,
					setCycle			EndLoop self,
				)
			)

			(bothHaveWine
				(aPatti
					setCycle			EndLoop ,
				)
				(ego
					view				vP&LDoingIt,
					loop				lLdrinkingWine,
					cel				0,
					setCycle			EndLoop ,
				)
				(= cycles 22)
			)

			(lowerGlasses
				(aPatti
					setCycle			BegLoop ,
				)
				(ego
					setCycle			BegLoop ,
				)
				(= cycles 22)
				(if ( > 3 (++ drinkCounter))
					(-= state 2)
				)
			)

			(weveDrunkEnough
				(Format @string "\"So, Patti,
					how did you get started in the music business?\"")
				(= seconds (- (PrintLarry) 1)) 
			)

			(theyKiss
				(Format @string "\"Shut up, Larry!\"")
				(= seconds (PrintPatti))
				(ego					hide)
				(aPatti
					loop				lBothKissing,
					cel				0,
					setCycle			EndLoop ,
				)
				(music
					number			sFirstKiss,
					loop				2,
					play,
				)
			)

			(unKiss
				(aPatti 
					setCycle			CycleTo 3 ctReverse,
				)
				(= cycles 22)
			)

			(ohPatti
				(Format @string "\"Oh, Patti.\"")
				(= seconds (PrintLarry))
			)

			(ohLarry
				(Format @string "\"Oh, Larry!\"")
				(= seconds (PrintPatti))
			)

			(kiss2
				(aPatti
					setCycle			EndLoop ,
				)
				(= cycles 44)
			)

			(unKiss2
				(aPatti 
					setCycle			CycleTo 3 ctReverse,
				)
				(= cycles 8)
			)

			(ohPatti2
				(Format @string "\"Wherever did you learn to kiss like that?\"")
				(= seconds (PrintPatti))
			)

			(ohLarry2
				(Format @string "\"Well, when I was younger I DID play
					trumpet in a mariachi band...\"")
				(= seconds (PrintLarry)) 
			)

			(rollOnTop
				(aPatti
					loop				lBothRollingOntoP,
					cel				0,
					setCycle			EndLoop ,
				)
				(= cycles 33)
			)

			(gotoBlack
				(curRoom drawPic blackPicture IRISIN)
				(cast eachElementDo #hide)
				(systemWindow
					color 			ltRed,
					back				ltBlack,
				)
				(music
					number			sFadeToBlack1,
					loop				2,
					play,
				)
				(= cycles 30)
			)

			(ohLarry3
				(Format @string "\"OH, LARRY!\"")
				(= seconds (PrintPatti))
			)

			(mmmmmm
				(Format @string "\"Oh! (mmmmmhhh)! Ah.\"")
				(= seconds (PrintPatti))
			)

			(ahhh
				(Format @string "\"Ohhhhh! (aaaaahhh)!\"")
				(= seconds (- (PrintPatti) 1))
			)

			(youAreTheFirst
				(Format @string "\"Larry, (oooh) 
					you're the first man (aaaahhh)
					who's ever made me feel (mmmmmhhh) 
					THAT!\"")
				(= seconds (- (PrintPatti) 1))
			)

			(whereDidYou
				(Format @string "\"Where did (ooohh)
					you  learn (oooohhhhhh)
					to do (uuuhhhh) 
					that?!\"")
				(= seconds (- (PrintPatti) 1))
			)

			(andThat
				(Format @string "\"And that!\"")
				(= seconds (- (PrintPatti) 2))
			)

			(yesThat
				(Format @string "\"And, oh yes, THAT! 
					Yes, THAT!!
					Please, more of that!!\"")
				(= seconds (- (PrintPatti) 2))
			)

			(lafferLover
				(Format @string "Well, well, Larry.
					Perhaps you learned more from Kalalau than you realized.")
				(Print @string
					#time			(PrintDelay @string)
				)
				(Format @string 
					"No longer is our Larry Laffer the Laugher Lover!")
				(Print @string
					#time			(PrintDelay @string)
				)
				(= seconds (+ 3 (PrintDelay @string)))
			)

			(changeRooms
				(curRoom newRoom 481)
			)



			;**	What a dumb bastard! He forgot the wine...

			(preventPenetration
				(ego 
					setMotion		MoveTo (ego x) 180 self,
				)
			)

			(comfortable
				(Print "\"Oh, Larry, I'm so glad you came.
					As you can see, I slipped into something more comfortable_--
					my bed!
					But, I'm not naked: I'm wearing your lei!\"" BIG)
				(Print "(All right, Larry! You're on, big guy!)" LOW)
				(delay 3)
			)

			(didYouBringIt
				(Print "\"Did you bring the wine?\" Patti asks softly.
					\"I'd love a drink right now.\"" BIG)
				(music
					number			sInterrupted,
					loop				1,
					play 				self,			
				)
				(Printf "\"%s\" you reply,
					\"I knew there was something I was supposed to do!
					I'll be right back!! Don't move!\"" expletive)
			)

			(backToVator
				(ego
					setMotion		MoveTo (ego x) 192 ,
				)
			)
		)
	)
)

(instance aPatti of Prop
	(properties
		view					vP&LDoingIt					
		loop					lPtakingWine
		x						151
		y						101
		cycleSpeed			1
	)
	(method (init)
		(super init)
		(self
			ignoreActors,
			setPri			ltBlack,
			stopUpd,
		)
	)
)

(procedure (PrintPatti &tmp t)
	(Print @string
		#at 			10 10
		#title		{Patti says...}
		#width 		140
		#mode			teJustCenter
		#time			(= t (PrintDelay @string))
		#dispose
	)
	(return (+ 3 t))
)
(procedure (PrintLarry &tmp t)
	(Print @string
		#at 			160 10
		#title		{You say...}
		#width 		140
		#mode			teJustCenter
		#time			(= t (PrintDelay @string))
		#dispose
	)
	(return (+ 3 t))
)
